import { createAction, props } from '@ngrx/store';
import { IPrices } from 'src/app/shared/models/prices';

export const getBalance = createAction(
    '[Dashboard Component] Get Balance',
    props<{balance: string}>()
)

export const latestPrices = createAction(
    '[Dashboard Component] Load Latest Prices'
)

export const loadingPricesSuccess = createAction(
    '[Dashboard Component] Load Prices Success', 
    props<{prices: IPrices[]}>()
)

export const loadingPricesFail = createAction(
    '[Dashboard Component] Load Prices Fail',
)