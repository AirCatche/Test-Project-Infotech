object Dependencies {

    object Kotlin {

        const val COROUTINES_ANDROID =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_COROUTINES_ANDROID}"
        const val SERIALIZATION_JSON =
                "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION_JSON}"
    }

    object Android {

        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    }

    object AndroidX {

        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val DATASTORE_COMMON =
                "androidx.datastore:datastore-preferences-core:${Versions.DATASTORE}"
        const val DATASTORE_ANDROID =
                "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
        const val J_UNIT = "androidx.test.ext:junit:${Versions.J_UNIT}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"

        object Ktx {

            const val CORE = "androidx.core:core-ktx:${Versions.CORE_KTX}"
            const val LIFECYCLE_ANNOTATIONS =
                    "androidx.lifecycle:lifecycle-common-java8:${Versions.LIFECYCLE}"
            const val LIFECYCLE_VIEWMODEL =
                    "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
            const val LIFECYCLE_RUNTIME =
                    "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
            const val FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

            const val ROOM = "androidx.room:room-ktx:${Versions.ROOM}"
            const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"

            object Navigation {

                const val FRAGMENT =
                        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
                const val UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
            }
        }
    }

    object Dagger {

        const val DAGGER_HILT = "com.google.dagger:hilt-android:${Versions.DAGGER_HILT}"
        const val DAGGER_HILT_COMPILER =
                "com.google.dagger:hilt-android-compiler:${Versions.DAGGER_HILT}"
        const val DAGGER_HILT_COMPILER_X =
                "androidx.hilt:hilt-compiler:${Versions.DAGGER_HILT_NAVIGATION}"
        const val DAGGER_HILT_COMPOSE =
                "androidx.hilt:hilt-navigation-compose:${Versions.DAGGER_HILT_NAVIGATION}"
        const val DAGGER_HILT_LIFECYCLE_VIEWMODEL =
                "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.DAGGER_HILT_LIFECYCLE}"
    }

    object Square {

        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val MOSHI = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
        const val MOSHI_CONVERTER =
                "com.squareup.retrofit2:converter-moshi:${Versions.MOSHI_CONVERTER}"
        const val RETROFIT_JSON =
                "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.RETROFIT_JSON}"
    }

    object Glide {

        const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    }
}
