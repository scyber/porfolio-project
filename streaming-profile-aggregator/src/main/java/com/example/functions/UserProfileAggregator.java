package com.example.functions;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import com.example.model.CategoryEvent;
import com.example.model.EnrichedEvent;
import com.example.model.UserProfileUpdated;

public class UserProfileAggregator
        extends KeyedProcessFunction<String, EnrichedEvent, UserProfileUpdated> {

    private static final long serialVersionUID = 1L;
    private transient ValueState<UserProfileState> profileState;

    public void open(Configuration parameters) {
        ValueStateDescriptor<UserProfileState> descriptor = new ValueStateDescriptor<>(
                "userProfileState",
                UserProfileState.class);
        profileState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public void processElement(
            EnrichedEvent event,
            KeyedProcessFunction<String, EnrichedEvent, UserProfileUpdated>.Context ctx,
            Collector<UserProfileUpdated> out) throws Exception {

        UserProfileState state = profileState.value();
        if (state == null) {
            state = new UserProfileState();
        }

        updateInterests(state, event);
        updateRecentItems(state, event);
        updateActivityScore(state, event);

        profileState.update(state);

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