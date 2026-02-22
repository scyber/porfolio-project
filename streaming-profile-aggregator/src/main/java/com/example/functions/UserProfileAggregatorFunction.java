package com.example.functions;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import com.example.model.CategoryEvent;
import com.example.model.EnrichedEvent;
import com.example.model.UserProfileUpdated;

public class UserProfileAggregatorFunction
        extends KeyedProcessFunction<String, EnrichedEvent, UserProfileUpdated> {

    private static final long serialVersionUID = 1L;
    // private transient ValueState<UserProfileState> profileState;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(UserProfileAggregatorFunction.class);

    // public void open(Configuration parameters) {
    // ValueStateDescriptor<UserProfileState> descriptor = new
    // ValueStateDescriptor<>(
    // "userProfileState",
    // UserProfileState.class);
    // logger.info("Initializing UserProfileState ValueState");
    // logger.info("UserProfileState Descriptor: {}", descriptor);
    // profileState = getRuntimeContext().getState(descriptor);
    // logger.info("UserProfileState ValueState initialized: {}", profileState);
    // }

    @Override
    public void processElement(
            EnrichedEvent event,
            KeyedProcessFunction<String, EnrichedEvent, UserProfileUpdated>.Context ctx,
            Collector<UserProfileUpdated> out) throws Exception {

        ValueStateDescriptor<UserProfileState> descriptor = new ValueStateDescriptor<>(
                "userProfileState",
                UserProfileState.class);
        logger.info("Initializing UserProfileState ValueState");

        ValueState<UserProfileState> profileState = getRuntimeContext().getState(descriptor);

        UserProfileState state = profileState.value();
        logger.info("Retrieved UserProfileState from state: {}", state);
        if (state == null) {
            logger.info("UserProfileState is null, creating new one");
            state = new UserProfileState();
        }

        updateInterests(state, event);
        logger.info("Updated interests in UserProfileState: {}", state.getInterests());
        updateRecentItems(state, event);
        logger.info("Updated recent items in UserProfileState: {}", state.getRecentItems());
        updateActivityScore(state, event);
        logger.info("Updated activity score in UserProfileState: {}", state.getActivityScore());
        profileState.update(state);
        logger.info("Updated UserProfileState stored back to state: {}", state);

        UserProfileUpdated updated = new UserProfileUpdated(
                event.getUserId(),
                System.currentTimeMillis(),
                state.toProfile());

        out.collect(updated);
    }

    private void updateInterests(UserProfileState state, EnrichedEvent event) {
        if (event.getEnrichment() == null ||
                event.getEnrichment().getCategory() == null) {
            return;
        }
        CategoryEvent category = event.getEnrichment().getCategory();
        double oldValue = state.getInterests().getOrDefault(category, 0.0);
        double newValue = oldValue * 0.9 + 0.1; // decay + boost
        state.getInterests().put(category, newValue);

    }

    private void updateRecentItems(UserProfileState state, EnrichedEvent event) {
        if (event.getContext() != null && event.getContext().itemId() != null) {
            state.getRecentItems().addFirst(event.getContext().itemId());
            if (state.getRecentItems().size() > 20) {
                state.getRecentItems().removeLast();
            }
        }
    }

    private void updateActivityScore(UserProfileState state, EnrichedEvent event) {
        double score = state.getActivityScore();
        score = score * 0.95 + 0.05; // простое сглаживание
        state.setActivityScore(score);
    }
}