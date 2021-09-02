package com.example.assignment4.upload

import org.springframework.web.multipart.MultipartFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/storage/")
class BucketController @Autowired
internal constructor(private val amazonClient: AmazonClient) {

    internal inner class StringResponse(private val response: String)// get/set omitted...

    @CrossOrigin(origins = arrayOf("http://localhost:8100"))
    @PostMapping("/uploadFile")
    fun uploadFile(@RequestPart(value = "file") file: MultipartFile): String {
        return "{ \"imageUrl\": \"${this.amazonClient.uploadFile(file)}\"}"
    }

    @CrossOrigin(origins = arrayOf("http://localhost:8100"))
    @DeleteMapping("/deleteFile")
    fun deleteFile(@RequestPart(value = "url") fileUrl: String): String {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl)
    }
}