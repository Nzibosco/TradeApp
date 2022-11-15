import { createReducer, on } from '@ngrx/store';
import { IPrices } from 'src/app/shared/models/prices';
import * as DashboardActions from './dashboard.actions';

export const dashboardFeatureKey = 'dashboard'; 

export interface DashboardState {
    aggBalance: string;
    USD: string;
    ETH: string;
    BNB: string;
    BTC: string; 
    DOGE: string;
    prices: IPrices[];
    balances:[];
}

export const initialDashboardState: DashboardState = {
    aggBalance: '$00.00',
    USD: '$00.00',
    ETH: '$00.00',
    BNB: '',
    BTC: '$00.00',
    DOGE: '$00.00',
    prices: [],
    balances: []
}

export const dashboardReducer = createReducer(
    initialDashboardState,
    on(DashboardActions.getBalance,
        (state, {...payload}) => ({
            ...state,
            aggBalance: payload.balance
        })
        ),

        on(DashboardActions.loadingPricesSuccess, 
            (state, {...payload}) => ({
                ...state, 
                prices: payload.prices
            })
            )
)