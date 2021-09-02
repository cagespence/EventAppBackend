package com.example.assignment4.upload

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.example.assignment4.errors.CustomException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import javax.annotation.PostConstruct
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

@Service
class AmazonClient {

    private var s3client: AmazonS3? = null

    @Value("\${amazonProperties.endpointUrl}")
    private val endpointUrl: String? = null
    @Value("\${amazonProperties.bucketName}")
    private val bucketName: String? = null
    @Value("\${amazonProperties.accessKey}")
    private val accessKey: String? = null
    @Value("\${amazonProperties.secretKey}")
    private val secretKey: String? = null

    @PostConstruct
    private fun initializeAmazon() {
        val credentials = BasicAWSCredentials(this.accessKey!!, this.secretKey!!)
        this.s3client = AmazonS3Client(credentials)
    }

    fun uploadFile(multipartFile: MultipartFile): String {
        var fileUrl = ""
        var file: File? = null;
        try {
            file = convertMultiPartToFile(multipartFile)
            val fileName = generateFileName(multipartFile)
            fileUrl = "$endpointUrl/$bucketName/$fileName"
            uploadFileTos3bucket(fileName, file)
            file.delete()
        } catch (e: Exception) {
            file?.delete()
            throw e.message?.let { CustomException(it) }!!;
        }

        return fileUrl
    }

    @Throws(IOException::class)
    private fun convertMultiPartToFile(file: MultipartFile): File {
        val convFile = File(file.originalFilename!!)
        val fos = FileOutputStream(convFile)
        fos.write(file.bytes)
        fos.close()
        return convFile
    }

    private fun generateFileName(multiPart: MultipartFile): String {
        return Date().time.toString() + "-" + multiPart.originalFilename!!.replace(" ", "_")
    }

    private fun uploadFileTos3bucket(fileName: String, file: File) {
        s3client!!.putObject(PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead))
    }

    fun deleteFileFromS3Bucket(fileUrl: String): String {
        val fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1)
        s3client!!.deleteObject(DeleteObjectRequest(bucketName, fileName))
        return "Successfully deleted"
    }

}