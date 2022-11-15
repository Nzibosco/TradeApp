import { createFeatureSelector, createSelector } from '@ngrx/store';
import { dashboardFeatureKey, DashboardState } from './dashboard.reducer';

export const getDashboardState = createFeatureSelector<DashboardState>(dashboardFeatureKey);

export const selectLatestPrices = createSelector(
    getDashboardState,
    (dashboardState: DashboardState) => dashboardState.prices
)