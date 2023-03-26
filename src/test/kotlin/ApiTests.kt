import com.google.gson.Gson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import data.singleUser.GetUserResponseDataClass
import data.user.CreateUserRequestDataClass
import data.user.CreatedUserResponseDataClass
import data.users_list.UsersList
import io.qameta.allure.Description
import org.junit.jupiter.api.DisplayName
import requests.UserRequests

@DisplayName("Api тесты")
class ApiTests {

    private val gson = Gson()

    private val userJson = gson.toJson(CreateUserRequestDataClass( "Vanya", "qa"))

    private val newUserData = gson.toJson(CreateUserRequestDataClass( "Sasha", "qa"))

    private val usersRequests = UserRequests()


    @Test
    @DisplayName("Тест на создание пользователя")
    fun createUserTest() {
        val response = usersRequests.createUser(userJson)
        val createdUserResponseDataClass = gson.fromJson(response, CreatedUserResponseDataClass::class.java)
        Assertions.assertNotNull(createdUserResponseDataClass.id)
    }

    @Test
    @DisplayName("Тест на получение пользователя")
    fun getUserPositive(){
        val response = usersRequests.getSingleUser(userID = "2", 200)
        val singleUser = gson.fromJson(response, GetUserResponseDataClass::class.java)
        Assertions.assertEquals(singleUser.data.first_name, "Janet")
    }

    @Test
    @DisplayName("Негативный тест на получение пользователя")
    fun getUserNegativeTest(){
        usersRequests.getSingleUser("sf", 404)
    }

    @Test
    @DisplayName("Тест на изменение пользователя")
    @Description("Этот тест будет падать")
    fun putUserDataTest(){
        val createdUserResponse = gson.fromJson(usersRequests.createUser(userJson), CreatedUserResponseDataClass::class.java)
        val userId = createdUserResponse.id
        usersRequests.putUserData(newUserData, userId.toString(), 200 )
        val getUserResponse = gson.fromJson(usersRequests.getSingleUser(userId.toString(), 200), GetUserResponseDataClass::class.java)
        Assertions.assertEquals("Sasha", getUserResponse.data.first_name)
    }

    @Test
    @DisplayName("Тест на получение списка  пользователей")
    fun getUsersListTest(){
        val response = usersRequests.getUsersList(200, "1")
        val usersList = gson.fromJson(response, UsersList::class.java)
        Assertions.assertEquals("george.bluth@reqres.in",  usersList.data[0].email)
    }

    @Test
    @DisplayName("Негативный тест на получение списка  пользователей")
    @Description("Этот тест будет падать")
    fun getUsersListNegativeTest(){
        usersRequests.getUsersList(404, "9999")
    }
}
