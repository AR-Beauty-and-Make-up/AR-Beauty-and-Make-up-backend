package ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.controllers

import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.builders.TurnBuilder
import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.model.turn.Turn
import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.services.TurnService
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@SpringBootTest
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TurnControllerTest {

    @Autowired
    lateinit var turnService: TurnService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun ifWeAskForTurnsWeGetTheAllTurns(){
        mockMvc.perform(MockMvcRequestBuilders.get("/turns")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun addedANewTurnWeGetStatusOK(){
        var aTurn = TurnBuilder.aTurn().withName("German Dos Santos").withEmail("german@gmail.com").withDate(LocalDateTime.of(2021, 7, 27, 9,0)).build()
        val body = generateTurnBody(aTurn)

        mockMvc.perform(MockMvcRequestBuilders.post("/turn")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.clientName").value(aTurn.clientName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(dateFormat(aTurn.date)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.service").value(aTurn.service.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.contactNumber").value(aTurn.contactNumber))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(aTurn.email))
    }

    @Test
    fun ifWeEditATurnReturnsTheUpdatedTurn(){
        val aTurn = TurnBuilder.aTurn().withId(1).withDate(LocalDateTime.of(2021, 7, 4, 9, 0)).withContactClient(1177889944).build()

        val savedTurn = this.turnService.addTurn(aTurn)

        aTurn.date = LocalDateTime.of(2021, 8, 4, 9, 0)

        val body = generateTurnBody(aTurn)

        mockMvc.perform(MockMvcRequestBuilders.put("/turns/" + savedTurn.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.clientName").value(aTurn.clientName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(dateFormat(aTurn.date)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.service").value(aTurn.service.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.contactNumber").value(aTurn.contactNumber))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(aTurn.email))
    }

    @Test
    fun ifWeDeleteATurnReturnsStatus200(){

        val aTurn = TurnBuilder.aTurn().withId(1).withDate(LocalDateTime.of(2021, 7, 4, 9, 0)).withContactClient(1177889944).build()

        val savedTurn = this.turnService.addTurn(aTurn)

        mockMvc.perform(MockMvcRequestBuilders.delete("/turns/delete/" + savedTurn.id))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun addingATurnOnADateAlreadyTakenReturnsBadRequest(){
        var turn = TurnBuilder.aTurn().withName("German Dos Santos").withEmail("german@gmail.com").withDate(LocalDateTime.of(2021, 6, 20, 9, 0)).build()
        val body = generateTurnBody(turn)
        
        this.turnService.addTurn(turn)

        mockMvc.perform(MockMvcRequestBuilders.post("/turn")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body.toString()))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    private fun generateTurnBody(aTurn: Turn): JSONObject {
        var jsonTurn = JSONObject()
        jsonTurn.put("clientName", aTurn.clientName)
        jsonTurn.put("date", aTurn.date)
        jsonTurn.put("service", aTurn.service)
        jsonTurn.put("contactNumber", aTurn.contactNumber)
        jsonTurn.put("email", aTurn.email)

        return jsonTurn
    }

    private fun dateFormat(date: LocalDateTime): String {
        return date.toLocalDate().toString() + " " + date.toLocalTime().toString() + ":00"
    }


}