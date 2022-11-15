import { NgModule } from '@angular/core';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store'; 
import { DashboardEffect } from './state/dashboard.effects';
import { dashboardFeatureKey, dashboardReducer } from './state/dashboard.reducer';

@NgModule({
    imports: [
        EffectsModule.forFeature([DashboardEffect]),
        StoreModule.forFeature(dashboardFeatureKey, dashboardReducer)
    ],
})
export class DashboardModule {}