
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

    val dagger by lazy { "com.google.dagger:hilt-android:${Versions.dagger}" }
    val daggerCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.dagger}"}

    val espressoCore by lazy {"androidx.test.espresso:espresso-core:${Versions.espresso}"}

    val junitExt by lazy { "androidx.test.ext:junit:${Versions.junitExt}"}

    val composeMaterial by lazy {"androidx.compose.material:material:${Versions.material}"}

    val hiltWork by lazy {"androidx.hilt:hilt-work:${Versions.hilt}"}
    val hiltCompiler by lazy {"androidx.hilt:hilt-compiler:${Versions.hilt}"}
    val hiltNavigation by lazy {"androidx.hilt:hilt-navigation-compose:${Versions.hilt}"}

    val workKtx by lazy {"androidx.work:work-runtime-ktx:${Versions.work}"}

    val retrofit by lazy {"com.squareup.retrofit2:retrofit:${Versions.retrofit}"}
    val retrofitGson by lazy {"com.squareup.retrofit2:converter-gson:${Versions.retrofit}"}
}