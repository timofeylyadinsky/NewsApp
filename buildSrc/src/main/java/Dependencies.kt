
object Deps {
    val room by lazy {"androidx.room:room-runtime:${Versions.room}"}
    val roomCompiler by lazy {"androidx.room:room-compiler:${Versions.room}"}

    val coreKtx by lazy {"androidx.core:core-ktx:${Versions.core}"}

    val lifecycleRuntime by lazy {"androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"}

    val activityCompose by lazy {"androidx.activity:activity-compose:${Versions.activity}"}
    val junit by lazy {"junit:junit:${Versions.junit}"}
}