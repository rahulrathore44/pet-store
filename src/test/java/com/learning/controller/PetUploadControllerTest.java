package com.learning.controller;

import com.learning.dto.xml.PetIdList;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class PetUploadControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testUploadPetsFileJson() {
        String json = """
                [
                	{
                		"id": 3,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	},
                	{
                		"id": 4,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	}
                ]
                """.stripIndent().trim();
        var body = MultipartBody.builder()
                .addPart("file", "pets.json", MediaType.APPLICATION_JSON_TYPE, json.getBytes())
                .build();
        HttpRequest<?> request = HttpRequest.POST("/pet/upload?format=JSON", body).contentType(MediaType.MULTIPART_FORM_DATA_TYPE);
        var arguments = Argument.listOf(Integer.class);
        HttpResponse<List<Integer>> response = client.toBlocking().exchange(request, arguments);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(List.of(3, 4), response.body());
    }

    @Test
    void testUploadPetsFileJson_String() {
        String json = """
                [
                	{
                		"id": 5,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	},
                	{
                		"id": 6,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	}
                ]
                """.stripIndent().trim();
        var body = MultipartBody.builder()
                .addPart("file", "pets.json", MediaType.APPLICATION_JSON_TYPE, json.getBytes())
                .build();
        HttpRequest<?> request = HttpRequest.POST("/pet/upload?format=JSON", body).contentType(MediaType.MULTIPART_FORM_DATA_TYPE);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("[5,6]", response.body());
    }

    @Test
    void testUploadPetsFileXml() {
        String xml = """
                <pets>
                    <pet>
                        <id>7</id>
                        <name>Bruno</name>
                        <category>
                            <id>1</id>
                            <name>Dog</name>
                        </category>
                        <status>sold</status>
                        <photoUrls>
                            <photoUrls>http://localhost:8909/pics/dog.jpg</photoUrls>
                        </photoUrls>
                        <tags>
                            <tags>
                                <id>1</id>
                                <name>Four legs</name>
                            </tags>
                        </tags>
                    </pet>
                	<pet>
                        <id>8</id>
                        <name>Bruno</name>
                        <category>
                            <id>1</id>
                            <name>Dog</name>
                        </category>
                        <status>sold</status>
                        <photoUrls>
                            <photoUrls>http://localhost:8909/pics/dog.jpg</photoUrls>
                        </photoUrls>
                        <tags>
                            <tags>
                                <id>1</id>
                                <name>Four legs</name>
                            </tags>
                        </tags>
                    </pet>
                </pets>
                """.stripIndent().trim();

        HttpRequest<?> request = HttpRequest.POST("/pet/upload?format=XML", MultipartBody.builder()
                .addPart("file", "pets.xml", MediaType.APPLICATION_XML_TYPE, xml.getBytes())
                .build()).contentType(MediaType.MULTIPART_FORM_DATA_TYPE);

        HttpResponse<PetIdList> response = client.toBlocking().exchange(request, PetIdList.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(List.of(7, 8), response.body().getPetIds());
    }

    @Test
    void testUploadPetsFileXml_String() {
        String xml = """
                <pets>
                    <pet>
                        <id>9</id>
                        <name>Bruno</name>
                        <category>
                            <id>1</id>
                            <name>Dog</name>
                        </category>
                        <status>sold</status>
                        <photoUrls>
                            <photoUrls>http://localhost:8909/pics/dog.jpg</photoUrls>
                        </photoUrls>
                        <tags>
                            <tags>
                                <id>1</id>
                                <name>Four legs</name>
                            </tags>
                        </tags>
                    </pet>
                	<pet>
                        <id>10</id>
                        <name>Bruno</name>
                        <category>
                            <id>1</id>
                            <name>Dog</name>
                        </category>
                        <status>sold</status>
                        <photoUrls>
                            <photoUrls>http://localhost:8909/pics/dog.jpg</photoUrls>
                        </photoUrls>
                        <tags>
                            <tags>
                                <id>1</id>
                                <name>Four legs</name>
                            </tags>
                        </tags>
                    </pet>
                </pets>
                """.stripIndent().trim();

        HttpRequest<?> request = HttpRequest.POST("/pet/upload?format=XML", MultipartBody.builder()
                .addPart("file", "pets.xml", MediaType.APPLICATION_XML_TYPE, xml.getBytes())
                .build()).contentType(MediaType.MULTIPART_FORM_DATA_TYPE);

        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("<pets><id>9</id><id>10</id></pets>", response.body());
    }

    @Test
    void testUploadPetsBytesJson_String() {
        String json = """
                [
                	{
                		"id": 11,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	},
                	{
                		"id": 12,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	}
                ]
                """.stripIndent().trim();
        HttpRequest<?> request = HttpRequest.POST("/pet/upload/raw?format=JSON", json.getBytes())
                .contentType(MediaType.APPLICATION_OCTET_STREAM_TYPE);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("[11,12]", response.body());
    }

    @Test
    void testUploadPetsBytesJson() {
        String json = """
                [
                	{
                		"id": 13,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	},
                	{
                		"id": 14,
                		"name": "Bruno",
                		"category": {
                			"id": 1,
                			"name": "Dog"
                		},
                		"photoUrls": [
                			"http://localhost:8909/pics/dog.jpg"
                		],
                		"tags": [
                			{
                				"id": 1,
                				"name": "Four legs"
                			}
                		],
                		"status": "sold"
                	}
                ]
                """.stripIndent().trim();
        HttpRequest<?> request = HttpRequest.POST("/pet/upload/raw?format=JSON", json.getBytes())
                .contentType(MediaType.APPLICATION_OCTET_STREAM_TYPE);
        var argument = Argument.listOf(Integer.class);
        HttpResponse<List<Integer>> response = client.toBlocking().exchange(request, argument);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(List.of(13, 14), response.body());
    }

    @Test
    void testUploadPetsBytesXml() {
        String xml = """
                <pets>
                    <pet>
                        <id>17</id>
                        <name>Bruno</name>
                        <category>
                            <id>1</id>
                            <name>Dog</name>
                        </category>
                        <status>sold</status>
                        <photoUrls>
                            <photoUrls>http://localhost:8909/pics/dog.jpg</photoUrls>
                        </photoUrls>
                        <tags>
                            <tags>
                                <id>1</id>
                                <name>Four legs</name>
                            </tags>
                        </tags>
                    </pet>
                	<pet>
                        <id>18</id>
                        <name>Bruno</name>
                        <category>
                            <id>1</id>
                            <name>Dog</name>
                        </category>
                        <status>sold</status>
                        <photoUrls>
                            <photoUrls>http://localhost:8909/pics/dog.jpg</photoUrls>
                        </photoUrls>
                        <tags>
                            <tags>
                                <id>1</id>
                                <name>Four legs</name>
                            </tags>
                        </tags>
                    </pet>
                </pets>
                """.stripIndent().trim();

        HttpRequest<?> request = HttpRequest.POST("/pet/upload/raw?format=XML", xml.getBytes())
                .contentType(MediaType.APPLICATION_OCTET_STREAM_TYPE);

        HttpResponse<PetIdList> response = client.toBlocking().exchange(request, PetIdList.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(List.of(17, 18), response.body().getPetIds());
    }

    @Test
    void testUploadPetsBytesXml_String() {
        String xml = """
                <pets>
                    <pet>
                        <id>19</id>
                        <name>Bruno</name>
                        <category>
                            <id>1</id>
                            <name>Dog</name>
                        </category>
                        <status>sold</status>
                        <photoUrls>
                            <photoUrls>http://localhost:8909/pics/dog.jpg</photoUrls>
                        </photoUrls>
                        <tags>
                            <tags>
                                <id>1</id>
                                <name>Four legs</name>
                            </tags>
                        </tags>
                    </pet>
                	<pet>
                        <id>20</id>
                        <name>Bruno</name>
                        <category>
                            <id>1</id>
                            <name>Dog</name>
                        </category>
                        <status>sold</status>
                        <photoUrls>
                            <photoUrls>http://localhost:8909/pics/dog.jpg</photoUrls>
                        </photoUrls>
                        <tags>
                            <tags>
                                <id>1</id>
                                <name>Four legs</name>
                            </tags>
                        </tags>
                    </pet>
                </pets>
                """.stripIndent().trim();

        HttpRequest<?> request = HttpRequest.POST("/pet/upload/raw?format=XML", xml.getBytes())
                .contentType(MediaType.APPLICATION_OCTET_STREAM_TYPE);

        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("<pets><id>19</id><id>20</id></pets>", response.body());
    }
}
