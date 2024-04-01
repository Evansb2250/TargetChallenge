package com.target.targetcasestudy.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.DataStoreKeys.CURRENT_USER_ID
import com.target.targetcasestudy.core.nonExistentUserError
import com.target.targetcasestudy.core.userAlreadyExistError
import com.target.targetcasestudy.data.repo.fakes.UserDaoFake
import com.target.targetcasestudy.data.room.dao.UserDao
import com.target.targetcasestudy.data.room.models.UserEntity
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class UserRepostoryImplTest {
    private lateinit var userDao: UserDao
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var dataStore: DataStore<Preferences>

    private lateinit var userRepostory: UserRepository

    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()

        userDao = UserDaoFake()
        dataStore = mock()
        dispatcherProvider = mock()

        Dispatchers.setMain(testDispatcher)

        Mockito.`when`(dispatcherProvider.io).thenReturn(testDispatcher)

        userRepostory = UserRepostoryImpl(
            userDao = userDao,
            dataStore = dataStore,
            dispatcherProvider = dispatcherProvider
        )
    }

    @Test
    fun `getUser test fail user doesnt exist`() = runTest {
        //Given
        val userName = "user"
        val password = "pass"

        //when

        //Then
        val result = userRepostory.getUser(userName, password)

        assertThat(result).isEqualTo(
            AsyncResponse.Failed(
                message = nonExistentUserError
            )
        )
    }


    @Test
    fun `getUser test fail wrong credentials`() = runTest {
        //Given
        val userName = "user"
        val password = "pass"

        //when
        userDao.insertUser(
            UserEntity(
                userName = userName,
                password = "target"
            )
        )
        //Then
        val result = userRepostory.getUser(userName, password)

        assertThat(result).isEqualTo(
            AsyncResponse.Failed(
                message = "Wrong Credentials"
            )
        )
    }

    @Test
    fun `getUser test success`() = runTest {
        //Given
        val userName = "user"
        val password = "pass"

        val userEntity = UserEntity(
            userName = userName,
            password = password
        )


        //when
        userDao.insertUser(
            userEntity
        )
        //Then
        val result = userRepostory.getUser(userName, password)

        assertThat(result).isEqualTo(
            AsyncResponse.Success(
                data = userEntity
            )
        )
    }

    @Test
    fun `getCurrentUserId failed no preference`() = runTest {
        //Given


        //When
        Mockito.`when`(dataStore.data).thenReturn(mock())
        Mockito.`when`(dataStore.data.firstOrNull()).thenReturn(null)

        //Then
        val result = userRepostory.getCurrentUserId()

        assertThat(result).isEqualTo(
            AsyncResponse.Failed(
                data = null,
                message = "no preference file"
            )
        )
    }

    @Test
    fun `getCurrentUserId Success`() = runTest {
        //Given
        val fakePreferences: Preferences = mock()
        val fakeFlowPreference: Flow<Preferences> = flowOf(fakePreferences)
        //Given
        val userName = "user"
        val password = "pass"
        val userId = 1

        val userEntity = UserEntity(
            userId = userId,
            userName = userName,
            password = password
        )


        //when
        userDao.insertUser(
            userEntity
        )


        //When
        Mockito.`when`(dataStore.data).thenReturn(fakeFlowPreference)
        Mockito.`when`(fakePreferences[CURRENT_USER_ID]).thenReturn("${userId}")

        //Then
        val result = userRepostory.getCurrentUserId()

        assertThat(result).isEqualTo(
            AsyncResponse.Success(
                data = userEntity
            )
        )
    }


    @Test
    fun `getCurrentUserId failed no userId in datastore`() = runTest {
        //Given
        val fakePreferences: Preferences = mock()
        val fakeFlowPreference: Flow<Preferences> = flowOf(fakePreferences)
        //Given
        val userName = "user"
        val password = "pass"
        val userId = 1

        val userEntity = UserEntity(
            userId = userId,
            userName = userName,
            password = password
        )


        //when
        userDao.insertUser(
            userEntity
        )


        //When
        Mockito.`when`(dataStore.data).thenReturn(fakeFlowPreference)
        Mockito.`when`(fakePreferences[CURRENT_USER_ID]).thenReturn(null)

        //Then
        val result = userRepostory.getCurrentUserId()

        assertThat(result).isEqualTo(
            AsyncResponse.Failed(
                data = null,
                message = "no user"
            )
        )
    }


    @Test
    fun `create user test passed `() = runTest {
        //Given
        val userName = "user"
        val password = "pass"
        val userId = 1

        val userEntity = UserEntity(
            userId = userId,
            userName = userName,
            password = password
        )

        val result = userRepostory.createUser(userName, password)

        assertThat(result).isEqualTo(AsyncResponse.Success("User created!!"))
    }


    @Test
    fun `create user test fail user exist `() = runTest {
        //Given
        val userName = "user"
        val password = "pass"
        val userId = 1

        val userEntity = UserEntity(
            userId = userId,
            userName = userName,
            password = password
        )

        userDao.insertUser(userEntity)

        val result = userRepostory.createUser(userName, password)

        assertThat(result).isEqualTo(AsyncResponse.Failed(userAlreadyExistError))
    }

    @Test
    fun `delete User test pass`() = runTest {
        //Given
        val userName = "user"
        val password = "pass"
        val userId = 1

        val userEntity = UserEntity(
            userId = userId,
            userName = userName,
            password = password
        )

        //adds user to database
        userDao.insertUser(userEntity)

        //test to see if the user is stored
        val loginResponse = userRepostory.getUser(userName, password)

        assertThat(loginResponse).isEqualTo(AsyncResponse.Success(userEntity))

        //delete the user
        userRepostory.deleteUser(userId = userId.toString())


        val deletionResponse = userRepostory.getUser(userName, password)

        //Test to see if the user has been deleted.
        assertThat(deletionResponse).isEqualTo(
            AsyncResponse.Failed(
                message = nonExistentUserError
            )
        )
    }


    @Test
    fun `authenticate User Id test pass`() = runTest {
        //Given
        val userName = "user"
        val password = "pass"
        val userId = 1

        val userEntity = UserEntity(
            userId = userId,
            userName = userName,
            password = password
        )

        //adds user to database
        userDao.insertUser(userEntity)

        //test to see if the user is stored
        val loginResponse = userRepostory.authenticateUserId(userId = userId)

        assertThat(loginResponse).isEqualTo(AsyncResponse.Success(userEntity))

    }


    @Test
    fun `authenticate User Id test fail user id doesn't exist`() = runTest {
        //Given
        val userName = "user"
        val password = "pass"
        val userId = 1
        val nonexistentUserId = 43

        val userEntity = UserEntity(
            userId = userId,
            userName = userName,
            password = password
        )

        //adds user to database
        userDao.insertUser(userEntity)

        //test to see if the user is stored
        val loginResponse = userRepostory.authenticateUserId(userId = nonexistentUserId)

        assertThat(loginResponse).isEqualTo(
            AsyncResponse.Failed(
                message = nonExistentUserError
            )
        )

    }


}