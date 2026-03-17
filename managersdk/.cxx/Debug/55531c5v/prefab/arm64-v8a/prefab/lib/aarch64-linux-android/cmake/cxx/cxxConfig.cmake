if(NOT TARGET cxx::cxx)
add_library(cxx::cxx STATIC IMPORTED)
set_target_properties(cxx::cxx PROPERTIES
    IMPORTED_LOCATION "/Users/manjiale/.gradle/caches/8.14/transforms/a7f41ab55a11ac76a0fd4e1442bc9266/transformed/libcxx-27.0.12077973/prefab/modules/cxx/libs/android.arm64-v8a/libcxx.a"
    INTERFACE_INCLUDE_DIRECTORIES "/Users/manjiale/.gradle/caches/8.14/transforms/a7f41ab55a11ac76a0fd4e1442bc9266/transformed/libcxx-27.0.12077973/prefab/modules/cxx/include"
    INTERFACE_LINK_LIBRARIES ""
)
endif()

