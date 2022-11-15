import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects'; 
import { EMPTY, of } from 'rxjs';
import { map, mergeMap, catchError } from 'rxjs';
import { DashboardService } from 'src/app/services/dashboard.service';
import * as DashboardActions from './dashboard.actions';


@Injectable()
export class DashboardEffect {


    getLatestPrices$ = createEffect(() => this.actions$.pipe(
        ofType(DashboardActions.latestPrices),
        mergeMap(() => this.dashboardService.getPrices()
        .pipe(
            map(prices => ({type: '[Dashboard Component] Load Prices Success', payload: prices})),
            catchError(() => of({type: '[Dashboard Component] Load Prices Fail'}))
        )
        )
    ));

    constructor(
        private actions$: Actions,
        private dashboardService: DashboardService
    ){}
}