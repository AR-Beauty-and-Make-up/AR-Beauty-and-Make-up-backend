package ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.controllers

import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.model.turn.Turn
import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.services.TurnService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime


@RestController
@Transactional
@EnableAutoConfiguration
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class TurnController {

    @Autowired
    lateinit var turnService: TurnService


    @GetMapping("/turns")
    fun getTurns(): ResponseEntity<List<Turn>> {

        val turns = turnService.getTurns()
        val response = ResponseEntity.status(HttpStatus.OK).body(turns)

        return response
    }

    @GetMapping("/turns/{date}")
    fun getTurnsByDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate): ResponseEntity<List<Turn>> {
        val turns = turnService.findAllByDate(date)
        val response = ResponseEntity.status(HttpStatus.OK).body(turns)

        return response
    }

    @PostMapping("/turn")
    fun createTurn(@RequestBody aTurn: Turn): ResponseEntity<Turn> {

        return ResponseEntity.status(HttpStatus.OK).body(turnService.addTurn(aTurn))
    }

    @RequestMapping("turns/{id}", method = [RequestMethod.PUT])
    fun updateTurn(@RequestBody aTurn: Turn, @PathVariable("id") id: String): ResponseEntity<Turn>{
        var turnId = id.toLong()
        var turnToUpdate = turnService.updateTurn(turnId, aTurn)

        return ResponseEntity.status(HttpStatus.OK).body(turnToUpdate)
    }

    @RequestMapping("turns/delete/{id}", method = [RequestMethod.DELETE])
    fun deleteTurn(@PathVariable("id") id: String): HttpStatus{
        var turnId = id.toLong()
        var aTurn = turnService.find(turnId)
        turnService.deleteTurn(aTurn)

        return HttpStatus.OK
    }


    @GetMapping("/dates")
    fun getDates(): ResponseEntity<List<String>> {

        val dates = turnService.getDates()
        val response = ResponseEntity.status(HttpStatus.OK).body(dates)

        return response
    }

}