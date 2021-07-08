package ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.repository

import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.DatabaseInitializate
import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.builders.UserBuilder
import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.model.user.User
import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.persistence.UserRepository
import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.services.FlushService
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @MockBean
    lateinit var databaseInitializate: DatabaseInitializate

    @Test
    fun testUserRepositorySaveAndRetrievedAClientUser(){
        val clientUser: User = UserBuilder.aUser().build()
        userRepository.save(clientUser)

        val retrievedClienUser: User = userRepository.findById(clientUser.id!!).get()

        Assert.assertEquals(clientUser.fullname, retrievedClienUser.fullname)
    }

    @Test
    fun testUserRepositorySaveAndRetrievedAnAdminUser(){
        val adminUser: User = UserBuilder.aUser().buildAdmin()
        userRepository.save(adminUser)

        val retrievedAdminUser = userRepository.findById(adminUser.id!!).get()

        Assert.assertEquals(adminUser.fullname, retrievedAdminUser.fullname)
        Assert.assertTrue(retrievedAdminUser.isAdmin)
    }

    @Test
    fun testUserRepositoryUpdateAndRetrievedaClientUser(){
        val clientUser: User = UserBuilder.aUser().build()
        userRepository.save(clientUser)

        val retrievedClientUser: User = userRepository.findById(clientUser.id!!).get()
        retrievedClientUser.fullname = "Lucas Emiliano Avalos"

        userRepository.save(retrievedClientUser)

        Assert.assertEquals("Lucas Emiliano Avalos", retrievedClientUser.fullname)
    }
}