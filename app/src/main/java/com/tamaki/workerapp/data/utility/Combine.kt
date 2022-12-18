package com.tamaki.workerapp.data.utility

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class Combine {
    fun <T1, T2, T3, T4, T5, T6, R> six(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        transform: suspend (T1, T2, T3, T4, T5, T6) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6, ::Triple),
    ) { t1, t2 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third,
        )
    }
    fun <T1, T2, T3, T4, T5, T6, T7,R> seven(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, ::Pair),
        combine( flow6, flow7, ::Pair),
    ) { t1, t2, t3 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t3.first,
            t3.second,
        )
    }

    fun <T1, T2, T3, T4, T5, T6, T7,T8,R> eight(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7, T8) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, ::Pair),
        combine( flow6, flow7,flow8, ::Triple),
    ) { t1, t2, t3 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t3.first,
            t3.second,
            t3.third
        )
    }

    fun <T1, T2, T3, T4, T5, T6, T7,T8,T9,T10,R> ten(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        flow10: Flow<T10>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7, T8, T9, T10) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, ::Pair),
        combine( flow6, flow7,flow8, ::Triple),
        combine( flow9, flow10, ::Pair),
    ) { t1, t2, t3, t4 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t3.first,
            t3.second,
            t3.third,
            t4.first,
            t4.second
        )
    }

    fun <T1, T2, T3, T4, T5, T6, T7,T8,T9,T10,T11, T12,R> twelve(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        flow10: Flow<T10>,
        flow11: Flow<T11>,
        flow12: Flow<T12>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7, T8, T9, T10, T11, T12) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6,::Triple),
        combine(flow7,flow8,flow9, ::Triple),
        combine(flow10,flow11,flow12, ::Triple),
    ) { t1, t2, t3, t4 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third,
            t3.first,
            t3.second,
            t3.third,
            t4.first,
            t4.second,
            t4.third,
        )
    }

    fun <T1, T2, T3, T4, T5, T6, T7,T8,T9,T10,T11, T12,T13,R> thirteen(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        flow10: Flow<T10>,
        flow11: Flow<T11>,
        flow12: Flow<T12>,
        flow13: Flow<T13>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7, T8, T9, T10, T11, T12, T13) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6,::Triple),
        combine(  flow7,flow8,flow9, ::Triple),
        combine(  flow10, flow11, ::Pair),
        combine(  flow12, flow13, ::Pair),
    ) { t1, t2, t3, t4, t5 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third,
            t3.first,
            t3.second,
            t3.third,
            t4.first,
            t4.second,
            t5.first,
            t5.second
        )
    }

    fun <T1, T2, T3, T4, T5, T6, T7,T8,T9,T10,T11, T12,T13,T14,R> fourteen(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        flow10: Flow<T10>,
        flow11: Flow<T11>,
        flow12: Flow<T12>,
        flow13: Flow<T13>,
        flow14: Flow<T14>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7, T8, T9, T10, T11, T12, T13, T14) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6,::Triple),
        combine(  flow7,flow8,flow9, ::Triple),
        combine(  flow10, flow11, ::Pair),
        combine(  flow12, flow13,flow14, ::Triple),
    ) { t1, t2, t3, t4, t5 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third,
            t3.first,
            t3.second,
            t3.third,
            t4.first,
            t4.second,
            t5.first,
            t5.second,
            t5.third
        )
    }

    fun <T1, T2, T3, T4, T5, T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18, R> eighteen(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        flow10: Flow<T10>,
        flow11: Flow<T11>,
        flow12: Flow<T12>,
        flow13: Flow<T13>,
        flow14: Flow<T14>,
        flow15: Flow<T15>,
        flow16: Flow<T16>,
        flow17: Flow<T17>,
        flow18: Flow<T18>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18) -> R
    ): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6,flow7,
        flow8,flow9,flow10,flow11,flow12,flow13,flow14,flow15,flow16,flow17,flow18,) { args: Array<*> ->
        transform(
            args[0] as T1,
            args[1] as T2,
            args[2] as T3,
            args[3] as T4,
            args[4] as T5,
            args[5] as T6,
            args[6] as T7,
            args[7] as T8,
            args[8] as T9,
            args[9] as T10,
            args[10] as T11,
            args[11] as T12,
            args[12] as T13,
            args[13] as T14,
            args[14] as T15,
            args[15] as T16,
            args[16] as T17,
            args[17] as T18,
        )
    }

    fun <T1, T2, T3, T4, T5, T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19, R> nineteen(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        flow10: Flow<T10>,
        flow11: Flow<T11>,
        flow12: Flow<T12>,
        flow13: Flow<T13>,
        flow14: Flow<T14>,
        flow15: Flow<T15>,
        flow16: Flow<T16>,
        flow17: Flow<T17>,
        flow18: Flow<T18>,
        flow19: Flow<T19>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19) -> R
    ): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6,flow7,
        flow8,flow9,flow10,flow11,flow12,flow13,flow14,flow15,flow16,flow17,flow18,flow19,) { args: Array<*> ->
        transform(
            args[0] as T1,
            args[1] as T2,
            args[2] as T3,
            args[3] as T4,
            args[4] as T5,
            args[5] as T6,
            args[6] as T7,
            args[7] as T8,
            args[8] as T9,
            args[9] as T10,
            args[10] as T11,
            args[11] as T12,
            args[12] as T13,
            args[13] as T14,
            args[14] as T15,
            args[15] as T16,
            args[16] as T17,
            args[17] as T18,
            args[18] as T19,
        )
    }
    fun <T1, T2, T3, T4, T5, T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20, R> twenty(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        flow10: Flow<T10>,
        flow11: Flow<T11>,
        flow12: Flow<T12>,
        flow13: Flow<T13>,
        flow14: Flow<T14>,
        flow15: Flow<T15>,
        flow16: Flow<T16>,
        flow17: Flow<T17>,
        flow18: Flow<T18>,
        flow19: Flow<T19>,
        flow20: Flow<T20>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,) -> R
    ): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6,flow7,
        flow8,flow9,flow10,flow11,flow12,flow13,flow14,flow15,flow16,flow17,flow18,flow19,flow20,) { args: Array<*> ->
        transform(
            args[0] as T1,
            args[1] as T2,
            args[2] as T3,
            args[3] as T4,
            args[4] as T5,
            args[5] as T6,
            args[6] as T7,
            args[7] as T8,
            args[8] as T9,
            args[9] as T10,
            args[10] as T11,
            args[11] as T12,
            args[12] as T13,
            args[13] as T14,
            args[14] as T15,
            args[15] as T16,
            args[16] as T17,
            args[17] as T18,
            args[18] as T19,
            args[19] as T20,
        )
    }
    fun <T1, T2, T3, T4, T5, T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24, R> twentyFour(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        flow10: Flow<T10>,
        flow11: Flow<T11>,
        flow12: Flow<T12>,
        flow13: Flow<T13>,
        flow14: Flow<T14>,
        flow15: Flow<T15>,
        flow16: Flow<T16>,
        flow17: Flow<T17>,
        flow18: Flow<T18>,
        flow19: Flow<T19>,
        flow20: Flow<T20>,
        flow21: Flow<T21>,
        flow22: Flow<T22>,
        flow23: Flow<T23>,
        flow24: Flow<T24>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,) -> R
    ): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6,flow7,
        flow8,flow9,flow10,flow11,flow12,flow13,flow14,flow15,flow16,flow17,flow18,flow19,flow20,flow21,flow22,flow23,flow24,) { args: Array<*> ->
        transform(
            args[0] as T1,
            args[1] as T2,
            args[2] as T3,
            args[3] as T4,
            args[4] as T5,
            args[5] as T6,
            args[6] as T7,
            args[7] as T8,
            args[8] as T9,
            args[9] as T10,
            args[10] as T11,
            args[11] as T12,
            args[12] as T13,
            args[13] as T14,
            args[14] as T15,
            args[15] as T16,
            args[16] as T17,
            args[17] as T18,
            args[18] as T19,
            args[19] as T20,
            args[20] as T21,
            args[21] as T22,
            args[22] as T23,
            args[23] as T24,
        )
    }
}