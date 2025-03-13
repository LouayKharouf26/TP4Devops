package com.example.tp4;

import com.example.tp4.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(StudentControllerTest.class);

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

    private volatile boolean cpuConsumed = false;

    /**
     * Simulate CPU load for a given number of milliseconds.
     */
    private void consumeCpu(long millisToConsume) {
        long startTime = System.nanoTime();
        long duration = millisToConsume * 1_000_000; // Convert ms to ns
        while (System.nanoTime() - startTime < duration) {
            // Toggle a flag to prevent JIT optimization of a no-op loop
            cpuConsumed = !cpuConsumed;
        }
        logger.info("CPU consumption completed: {} ms", millisToConsume);
    }

    /**
     * Retrieves the system CPU usage as a percentage.
     */
    private double getCpuUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getSystemCpuLoad() * 100; // returns CPU usage in percentage
    }

    @Test
    public void addStudentTest() throws Exception {
        // Suggest running garbage collection to reduce noise in memory metrics
        System.gc();
        long startTime = System.nanoTime();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        double initialCpuUsage = getCpuUsage();
        logger.info("Initial CPU usage: {}%", initialCpuUsage);

        // Simulate workload by consuming CPU (for example, 180,000 ms = 3 minutes)
        consumeCpu(180000);

        // Create a Student object to be added
        Student student = Student.builder()
                .firstname("malek12345")
                .lastname("malek12345")
                .email("zaag.malek1@gmail.com")
                .build();

        // Execute POST request to add the student
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/addStudent")
                        .contentType("application/json")
                        .content(asJsonString(student))
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();

        double finalCpuUsage = getCpuUsage();
        logger.info("Final CPU usage: {}%", finalCpuUsage);

        long endTime = System.nanoTime();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long duration = endTime - startTime;
        long memoryUsed = endMemory - startMemory;
        double durationInSeconds = duration / 1_000_000_000.0;

        // Log the performance metrics in a structured (JSON-like) format
        logger.info("Test Metrics: {{ \"testName\": \"addStudentTest\", " +
                        "\"duration_seconds\": {}, \"memory_used_bytes\": {}, " +
                        "\"initial_cpu_percent\": {}, \"final_cpu_percent\": {} }}",
                durationInSeconds, memoryUsed, initialCpuUsage, finalCpuUsage);
    }

    @Test
    public void getStudentsTest() throws Exception {
        // Execute GET request to fetch all students
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        logger.info("getStudentsTest executed successfully.");
    }
}
