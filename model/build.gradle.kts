plugins {
    id("common-conventions")
}

dependencies {
    api(projects.common)
    ksp(libs.jimmer.ksp)
    implementation(libs.jimmer.sql.kotlin)
    implementation(libs.fastexcel)
    implementation(libs.jackson.kotlin)
}