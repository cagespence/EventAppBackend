package com.example.assignment4

import com.example.assignment4.entity.Tag
import com.example.assignment4.entity.User
import com.example.assignment4.repository.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.graphql.spring.boot.test.GraphQLResponse
import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit4.SpringRunner

import graphql.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.springframework.boot.test.context.SpringBootTest
import java.util.ArrayList
import org.junit.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GraphQLAPITests {

    @Autowired
    private val graphQLTestTemplate: GraphQLTestTemplate? = null // error suppressed as conflicting library dependencies are causing it, works regardless.

    @Autowired
    private val tagRepository: TagRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val eventRepository: EventRepository? = null

    @Autowired
    private val attendeeRepository: AttendeeRepository? = null

    @Autowired
    private val favoriteRepository: FavoriteRepository? = null

    @Autowired
    private val commentRepository: CommentRepository? = null

    private var currentValidToken: String = ""
    private var currentUserEmail: String = ""
    private var currentUserId: Int = -1

    private var currentUserPass: String = ""

//    companion object {
//        @Autowired
//        private val tagRepository: TagRepository? = null
//
//        @Autowired
//        private val eventRepository: EventRepository? = null
//
//        @Autowired
//        private val userRepository: UserRepository? = null
//
//        @Autowired
//        private val attendeeRepository: AttendeeRepository? = null
//
//        @Autowired
//        private val favoriteRepository: FavoriteRepository? = null
//
//        @Autowired
//        private val commentRepository: CommentRepository? = null
//
//        @BeforeAll
//        @JvmStatic
//        fun beforeClass() {
//            println("clearing DB beforeClass")
//            clearDb()
//        }
//
//        @AfterAll
//        @JvmStatic
//        fun afterClass() {
//            println("clearing DB afterClass")
//            clearDb()
//        }
//
//        private fun clearDb() {
//            commentRepository!!.deleteAll()
//            favoriteRepository!!.deleteAll()
//            attendeeRepository!!.deleteAll()
//            tagRepository!!.deleteAll()
//            eventRepository!!.deleteAll()
//            userRepository!!.deleteAll()
//        }
//    }

    private fun createEvent(variables: ObjectNode): GraphQLResponse {
        return graphQLTestTemplate!!.perform("graphql/mutation/create-new-event.graphql", variables)
    }

    private fun setUpAuthHeaders(graphQlTestTemplateObj: GraphQLTestTemplate) {
        graphQlTestTemplateObj.addHeader("Authorization", currentValidToken)
        graphQlTestTemplateObj.addHeader("UserEmail", currentUserEmail)
    }

    private fun byteArrayOfInts(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }

    private fun addTags(name: String): Tag {
        val newTag = Tag()
        newTag.setName(name)
        return tagRepository!!.save(newTag);
    }

    @Before
    fun authenticateUser() {
        println("Before triggered.")
        val userEmail = "testEmail123@test.test"
        val userPass = "password123"

        val newUser = User()
        newUser.setName("Test user")
        newUser.setDob("1999-05-20")
        newUser.setBio("Test bio")
        newUser.setPassword(BCryptPasswordEncoder().encode(userPass))
        newUser.setEmail(userEmail)

        val savedUser = userRepository!!.save(newUser)

        val variables = ObjectMapper().createObjectNode()
        variables.put("email", userEmail)
        variables.put("password", userPass)

        val response = graphQLTestTemplate!!.perform("graphql/mutation/sign-in-user.graphql", variables)
        currentValidToken = response.get("$.data.signInUser.token")
        currentUserEmail = response.get("$.data.signInUser.user.email")
        currentUserId = response.get("$.data.signInUser.user.id").toInt()
        setUpAuthHeaders(graphQLTestTemplate)
        println("Variables set for sending out authenticated requests.")
    }

    private fun clearDb() {
        tagRepository!!.deleteAll()
        commentRepository!!.deleteAll()
        favoriteRepository!!.deleteAll()
        attendeeRepository!!.deleteAll()
        eventRepository!!.deleteAll()
        userRepository!!.deleteAll()
    }

    @After
    fun deleteUser() {
        println("After each..")
//        val user = userRepository!!.findByEmail("testEmail123@test.test")
//        println("User found? " + user?.getEmail())
//        if (user != null) {
//            println("deleteing user")

//        userRepository!!.deleteAll()
        println("delted all")
        clearDb()
//            val user = userRepository!!.findByEmail("testEmail123@test.test")
//
//            if (user != null) {
//                println("we still have user...")
//            } else {
//                println("we yeeted hesae..")
//
//            }
//        }
    }

    @Test
    fun is_email_available() {
        val randomNum = (0..100).random()
        val emailAddress = "email$randomNum@test.com"

        val variables = ObjectMapper().createObjectNode()
        variables.put("email", emailAddress)

        val response = graphQLTestTemplate!!.perform("graphql/query/email-availability.graphql", variables)

        assertNotNull(response)
        assertTrue(response.isOk)
        // Currently this throws a conversion error from Boolean to String from the maven dependency used to test the graphQL
//        assertEquals(true, response.get("$.data.isEmailAvailable").toBoolean())
    }

    @Test
    fun register_new_user() {
        val response = graphQLTestTemplate!!.postForResource("graphql/mutation/register-new-user.graphql")
        assertNotNull(response)
        assertTrue(response.isOk)
        assertEquals("1999-01-01", response.get("$.data.createUser.dob"))
        assertEquals("newUserTest@test.test", response.get("$.data.createUser.email"))
        assertEquals("test", response.get("$.data.createUser.name"))
    }

    @Test
    fun sign_in_user() {

        val userEmail = "testEmail123@test.test"
        val userPass = "password123"

        val variables = ObjectMapper().createObjectNode()
        variables.put("email", userEmail)
        variables.put("password", userPass)

        val response = graphQLTestTemplate!!.perform("graphql/mutation/sign-in-user.graphql", variables)

        assertNotNull(response)
        assertTrue(response.isOk)
        assertNotNull(response.get("$.data.signInUser.token"))
        currentValidToken = response.get("$.data.signInUser.token")
        currentUserEmail = response.get("$.data.signInUser.user.email")
        currentUserId = response.get("$.data.signInUser.user.id").toInt()
        assertEquals(userEmail, response.get("$.data.signInUser.user.email"))
    }


    @Test
    fun create_event() {
        val variables = ObjectMapper().createObjectNode()
        variables.put("host", currentUserId)
        setUpAuthHeaders(graphQLTestTemplate!!)
        val response = createEvent(variables)
        assertNotNull(response)
        assertTrue(response.isOk)

        // Check event details.
        assertEquals("test-title", response.get("$.data.createEvent.title"))
        assertEquals("test-address", response.get("$.data.createEvent.address"))
        assertEquals("2000-10-20", response.get("$.data.createEvent.date"))
        assertEquals("test-description", response.get("$.data.createEvent.description"))
        assertEquals("03:00", response.get("$.data.createEvent.endTime"))
        assertEquals("02:00", response.get("$.data.createEvent.startTime"))
        assertEquals(this.currentUserId, response.get("$.data.createEvent.host.id").toInt())
        assertEquals(this.currentUserEmail, response.get("$.data.createEvent.host.email"))
        assertEquals("image-url", response.get("$.data.createEvent.imageUrl"))
    }

    // Comment on event
    @Test
    fun comment_on_event() {

        // Add new event first.
        val variablesForEvent = ObjectMapper().createObjectNode()
        variablesForEvent.put("host", currentUserId)
        setUpAuthHeaders(graphQLTestTemplate!!);
        val responseEvent = createEvent(variablesForEvent)

        // Comment variables
        val commentContent = "Can't wait to attend my own event"
        val eventId = responseEvent.get("$.data.createEvent.id").toInt()
        val variables = ObjectMapper().createObjectNode()
        variables.put("userId", currentUserId)
        variables.put("eventId", eventId)
        variables.put("comment", commentContent)

        // Perform variable
        val response = graphQLTestTemplate.perform("graphql/mutation/add-comment-to-event.graphql", variables)

        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
        assertNotNull(response.get("$.data.createComment.createdAt"))
        assertEquals(commentContent, response.get("$.data.createComment.content"))
        assertEquals(currentUserId.toInt(), response.get("$.data.createComment.author.id").toInt())
        assertEquals(eventId, response.get("$.data.createComment.event.id").toInt())
    }

    // Favorite event
    @Test
    fun favorite_event() {
        // Add new event first.
        val variablesForEvent = ObjectMapper().createObjectNode()
        variablesForEvent.put("host", currentUserId)
        setUpAuthHeaders(graphQLTestTemplate!!);
        val responseEvent = createEvent(variablesForEvent)

        // createFavorite variables
        val eventId = responseEvent.get("$.data.createEvent.id").toInt()
        val variables = ObjectMapper().createObjectNode()
        variables.put("userId", currentUserId)
        variables.put("eventId", eventId)

        // Perform variable
        val response = graphQLTestTemplate.perform("graphql/mutation/add-favorite-to-event.graphql", variables)

        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
        assertEquals(currentUserId, response.get("$.data.addFavorite.user.id").toInt())
        assertEquals(eventId, response.get("$.data.addFavorite.event.id").toInt())
        assertNotNull(response.get("$.data.addFavorite.id"))
    }

    // Unfavorite event
    @Test
    fun unfavorite_event() {
        // Add new event first.
        val variablesForEvent = ObjectMapper().createObjectNode()
        variablesForEvent.put("host", currentUserId)
        setUpAuthHeaders(graphQLTestTemplate!!);
        val responseEvent = createEvent(variablesForEvent)

        // createFavorite variables
        val variablesForFavorite = ObjectMapper().createObjectNode()
        variablesForFavorite.put("userId", currentUserId)
        variablesForFavorite.put("eventId", responseEvent.get("$.data.createEvent.id").toInt())

        // favorite the event
        graphQLTestTemplate.perform("graphql/mutation/add-favorite-to-event.graphql", variablesForFavorite)

        // removeFavorite variables
        val eventId = responseEvent.get("$.data.createEvent.id").toInt()
        val variables = ObjectMapper().createObjectNode()
        variables.put("userId", currentUserId)
        variables.put("eventId", eventId)

        // remove favorite from event
        val response = graphQLTestTemplate.perform("graphql/mutation/remove-favorite-from-event.graphql", variables)

        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
//        assertTrue(response.get("$.data.removeFavorite").toBoolean()) // Error within the GraphQLResponse obj, casting from string to boolean causes issues...
    }


    // Delete event (unattending your own event)
    @Test
    fun delete_event() {
        // Add new event first.
        val variablesForEvent = ObjectMapper().createObjectNode()
        variablesForEvent.put("host", currentUserId)
        setUpAuthHeaders(graphQLTestTemplate!!);
        val responseEvent = createEvent(variablesForEvent)

        // removeFavorite variables
        val eventId = responseEvent.get("$.data.createEvent.id").toInt()
        val variables = ObjectMapper().createObjectNode()
        variables.put("userId", currentUserId)
        variables.put("eventId", eventId)

        // delete event
        val response = graphQLTestTemplate.perform("graphql/mutation/delete-event.graphql", variables)

        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
//        assertTrue(response.get("$.data.removeEvent")) // Error within the GraphQLResponse obj, casting from string to boolean causes issues...
    }

    // Set as @Ignore currently as the current GraphQLTestTemplate implementation/version does not allow for array to be passed in as variables.
    @Test
    @Ignore
    fun add_tags_to_user() {
        val artTag = addTags("art")
        val scienceTag = addTags("science")
        val computersTag = addTags("computers")

        // Convert to byteArray so that we can use it in the variables.
        val tagIds = byteArrayOfInts(artTag.getId(), scienceTag.getId(), computersTag.getId())

        val test = ArrayList<Int>()

        val variables = ObjectMapper().createObjectNode()
        variables.put("userId", currentUserId)
        variables.put("tagIds", tagIds)

        val response = graphQLTestTemplate!!.perform("graphql/mutation/add-tag-to-user.graphql", variables)

        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
        assertNotNull(response.get("$.data.addInterest.id"))
        assertEquals(response.get("$.data.addInterest.tag[0].id").toInt(), artTag.getId())
        assertEquals(response.get("$.data.addInterest.tag[0].name"), artTag.getName())
        assertEquals(response.get("$.data.addInterest.user.id").toInt(), currentUserId)
    }

    @Test
    fun update_event() {
        // Add new event first.
        val variablesForEvent = ObjectMapper().createObjectNode()
        variablesForEvent.put("host", currentUserId)
        setUpAuthHeaders(graphQLTestTemplate!!);
        val responseEvent = createEvent(variablesForEvent)

        // updated variables
        val eventId = responseEvent.get("$.data.createEvent.id").toInt()
        val newAddress = "updated-address"
        val newDate = "2002-10-20"
        val newDescription = "updated-description"
        val newTitle = "updated-title"

        val variables = ObjectMapper().createObjectNode()
        variables.put("eventId", eventId)
        variables.put("address", newAddress)
        variables.put("date", newDate)
        variables.put("description", newDescription)
        variables.put("title", newTitle)

        // update event
        val response = graphQLTestTemplate.perform("graphql/mutation/update-event.graphql", variables)


        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
        assertEquals(newAddress, response.get("$.data.updateEvent.address"))
        assertEquals(newTitle, response.get("$.data.updateEvent.title"))
        assertEquals(currentUserId, response.get("$.data.updateEvent.host.id").toInt())
        assertEquals(newDescription, response.get("$.data.updateEvent.description"))
        assertEquals(newDate, response.get("$.data.updateEvent.date"))
        assertEquals(responseEvent.get("$.data.createEvent.id"), response.get("$.data.updateEvent.id"))
    }


    @Test
    fun get_event_by_id() {
        val variables = ObjectMapper().createObjectNode()
        variables.put("host", currentUserId)
        setUpAuthHeaders(graphQLTestTemplate!!)
        val eventResponse = createEvent(variables)

        val eventIdVariable = ObjectMapper().createObjectNode()
        eventIdVariable.put("eventId", eventResponse.get("$.data.createEvent.id").toInt())
        val response = graphQLTestTemplate.perform("graphql/query/get-event-by-id.graphql", eventIdVariable)

        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
        assertEquals(eventResponse.get("$.data.createEvent.id").toInt(), response.get("$.data.event.event.id").toInt())
        assertEquals(eventResponse.get("$.data.createEvent.title"), response.get("$.data.event.event.title"))
        assertEquals(eventResponse.get("$.data.createEvent.title"), response.get("$.data.event.event.title"))
    }

    @Test
    fun get_user_by_id() {
        val variables = ObjectMapper().createObjectNode()
        variables.put("id", currentUserId)
        setUpAuthHeaders(graphQLTestTemplate!!)
        val response = graphQLTestTemplate.perform("graphql/query/get-user-by-id.graphql", variables)

        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
        assertEquals(currentUserId, response.get("$.data.user.id").toInt())
    }

    @Test
    fun get_tag_by_id() {
        val newTestTag = addTags("newTestsTag")

        val variables = ObjectMapper().createObjectNode()
        variables.put("tagId", newTestTag.getId())
        setUpAuthHeaders(graphQLTestTemplate!!)
        val response = graphQLTestTemplate!!.perform("graphql/query/get-tag-by-id.graphql", variables)

        // Check results
        assertNotNull(response)
        assertTrue(response.isOk)
        assertEquals(newTestTag.getId(), response.get("$.data.tag.id").toInt())
        assertEquals(newTestTag.getName(), response.get("$.data.tag.name"))
    }

}
