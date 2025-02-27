package com.example.tp4;

import com.example.tp4.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private double getCpuUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getSystemCpuLoad() * 100; // Get CPU usage as a percentage
    }

    @Test
    public void addStudentTest() throws Exception {
        
        System.gc();
        long startTime = System.nanoTime();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        double initialCpuUsage = getCpuUsage();
        System.out.println("Initial CPU usage: " + initialCpuUsage + "%");
       
        Student student = Student.builder()
                .firstname("malek12345")
                .lastname("malek12345")
                .email("zaag.malek1@gmail.com")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/addStudent")
                        .contentType("application/json")
                        .content(asJsonString(student))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();

        
        double finalCpuUsage = getCpuUsage();
        System.out.println("Final CPU usage: " + finalCpuUsage + "%");
        long endTime = System.nanoTime();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long duration = endTime - startTime;
        long memoryUsed = endMemory - startMemory;
        double durationInSeconds = duration / 1_000_000_000.0;
        System.out.println("Test executed in: " + durationInSeconds + " seconds");
        System.out.println("Memory used: " + memoryUsed + " bytes");
    }

    @Test
    public void getStudentsTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
