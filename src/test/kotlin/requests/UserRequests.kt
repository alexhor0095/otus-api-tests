package requests

import io.qameta.allure.Step
import io.restassured.builder.RequestSpecBuilder
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification

class UserRequests {

    private fun getRequestSpec() : RequestSpecification {
        return RequestSpecBuilder()
            .setBaseUri("https://reqres.in/")
            .build()
    }

    @Step("Создание пользователя")
    fun createUser(body: String): String? {
            return Given {
                spec(getRequestSpec())
                body(body)
            } When {
                post("/api/users")
            } Then {
                statusCode(201)
            } Extract {
                body().jsonPath().getString("")
                asPrettyString()
            }
    }

    @Step("Получение пользователя по id")
    fun getSingleUser(userID: String, expectedStatusCode: Int): String?{
        return Given {
            spec(getRequestSpec())
        } When {
            get("/api/users/$userID")
        } Then {
            statusCode(expectedStatusCode)
        } Extract {
            body().jsonPath().getString("")
            asPrettyString()
        }
    }

    @Step("Изменение данных пользователя по id")
    fun putUserData(requestBody: String, userID: String, expectedStatusCode: Int): String?{
        return Given {
            spec(getRequestSpec())
            body(requestBody)
        } When {
            put("/api/users/$userID")
        } Then {
            statusCode(expectedStatusCode)
        } Extract {
            body().jsonPath().getString("")
            asPrettyString()
        }
    }

    @Step("Получение списка пользователей")
    fun getUsersList(expectedStatusCode: Int, pageNum: String): String?{
        return Given {
            spec(getRequestSpec())
        } When {
            get("/api/users?page=$pageNum")
        } Then {
            statusCode(expectedStatusCode)
        } Extract {
            body().jsonPath().getString("")
            asPrettyString()
        }
    }
}