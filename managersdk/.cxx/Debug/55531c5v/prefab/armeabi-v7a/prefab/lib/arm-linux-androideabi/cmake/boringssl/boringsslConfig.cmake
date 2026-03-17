if(NOT TARGET boringssl::crypto_static)
add_library(boringssl::crypto_static STATIC IMPORTED)
set_target_properties(boringssl::crypto_static PROPERTIES
    IMPORTED_LOCATION "/Users/manjiale/.gradle/caches/8.14/transforms/b42815ab2376a87ae6d728d5b5805572/transformed/boringssl-20250114/prefab/modules/crypto_static/libs/android.armeabi-v7a/libcrypto_static.a"
    INTERFACE_INCLUDE_DIRECTORIES "/Users/manjiale/.gradle/caches/8.14/transforms/b42815ab2376a87ae6d728d5b5805572/transformed/boringssl-20250114/prefab/modules/crypto_static/include"
    INTERFACE_LINK_LIBRARIES ""
)
endif()

if(NOT TARGET boringssl::ssl_static)
add_library(boringssl::ssl_static STATIC IMPORTED)
set_target_properties(boringssl::ssl_static PROPERTIES
    IMPORTED_LOCATION "/Users/manjiale/.gradle/caches/8.14/transforms/b42815ab2376a87ae6d728d5b5805572/transformed/boringssl-20250114/prefab/modules/ssl_static/libs/android.armeabi-v7a/libssl_static.a"
    INTERFACE_INCLUDE_DIRECTORIES "/Users/manjiale/.gradle/caches/8.14/transforms/b42815ab2376a87ae6d728d5b5805572/transformed/boringssl-20250114/prefab/modules/ssl_static/include"
    INTERFACE_LINK_LIBRARIES "boringssl::crypto_static"
)
endif()

