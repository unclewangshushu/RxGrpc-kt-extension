
import com.google.protobuf.GeneratedMessageLite
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.Method
import kotlin.reflect.KClass


fun <T> Single<T>.async(): Single<T> =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


inline fun <reified Q : GeneratedMessageLite<Q, QB>, QB : GeneratedMessageLite.Builder<Q, QB>,
        R : GeneratedMessageLite<R, RB>, RB : GeneratedMessageLite.Builder<R, RB>>
        ((Q) -> R).rx(block: QB.() -> Unit = { /* default empty block */ }): Single<R> =
        Single.just(
                Builders.of<QB>(Q::class).apply(block).build()
        ).map(this)


object Builders {
    private const val METHOD_NEW_BUILDER = "newBuilder"

    private val builderMethodCache = HashMap<KClass<*>, Method>()

    @Suppress("UNCHECKED_CAST")
    fun <QB> of(kClazz: KClass<*>) =
            builderMethodCache.getOrPut(kClazz, { kClazz.java.getMethod(METHOD_NEW_BUILDER) })
                    .invoke(null) as QB
}


