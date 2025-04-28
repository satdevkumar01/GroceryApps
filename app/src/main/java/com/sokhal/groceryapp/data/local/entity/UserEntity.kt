package com.sokhal.groceryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sokhal.groceryapp.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String?
) {
    fun toUser(): User {
        return User(
            id = id,
            name = name,
            email = email,
            profilePicture = profilePicture
        )
    }

    companion object {
        fun fromUser(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                name = user.name,
                email = user.email,
                profilePicture = user.profilePicture
            )
        }
    }
}