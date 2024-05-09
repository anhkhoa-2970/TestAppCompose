package vn.aris.baseappcompose.data.base

interface Mapper<E, M> {
	fun fromEntity(from: E): M
	fun toEntity(from: M): E
}

//interface MapperTwoWay<E, M> {
//	fun fromEntity(from: E): M
//	fun toEntity(from: M): E
//}

