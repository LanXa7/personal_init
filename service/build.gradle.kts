plugins {
    id("spring-conventions")
    id("test-conventions")
}

dependencies {
    api(projects.model)
    ksp(libs.jimmer.ksp)
    implementation(libs.bundles.spring.boot)
    implementation(libs.bundles.jimmer)
    implementation(libs.redisson)
    implementation(libs.minio)
    implementation(libs.jwt)
    implementation(libs.fastexcel)
    implementation(libs.spring.plugin)
    implementation(libs.logging)
    implementation(libs.okhttp3)
    implementation(libs.bundles.flyway)
    implementation(libs.postgresql)
    implementation(libs.jackson.kotlin)
    implementation(libs.jcasbin)
}