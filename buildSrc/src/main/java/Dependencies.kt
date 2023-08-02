
object Deps {
    val room by lazy {"androidx.room:room-runtime:${Versions.room}"}
    val roomCompiler by lazy {"androidx.room:room-compiler:${Versions.room}"}

    val coreKtx by lazy {"androidx.core:core-ktx:${Versions.core}"}

    val lifecycleRuntime by lazy {"androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"}

    val activityCompose by lazy {"androidx.activity:activity-compose:${Versions.activity}"}
    val junit by lazy {"junit:junit:${Versions.junit}"}

    val composeUI by lazy {  "androidx.compose.ui:ui" }
    val composeGraphic by lazy {"androidx.compose.ui:ui-graphics"}
    val composePreview by lazy {"androidx.compose.ui:ui-tooling-preview"}
    val material3 by lazy {"androidx.compose.material3:material3"}
    val composeBom by lazy {"androidx.compose:compose-bom:${Versions.bom}"}

    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}"}
}