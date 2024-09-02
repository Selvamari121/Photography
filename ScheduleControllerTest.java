package com.payment.schedule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
// import static org.mockito.Mockito.when;
// import static org.hamcrest.Matchers.is;

import org.springframework.http.MediaType;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.payment.schedule.controller.ScheduleController;
import com.payment.schedule.model.Schedule;
import com.payment.schedule.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService service;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach                                            
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testAddSchedule() throws Exception {
        Schedule photography = new Schedule();
        photography.setTotal_amount(100.0);
        photography.setSchedule_amount(50.0);
        photography.setPayment_due_date(LocalDate.of(2024, 8, 28));  
        photography.setIs_paid(true);
        photography.setPayment_due_date(LocalDate.of(2024, 8, 28)); 
        photography.setProject_id( 101L);

        String json = objectMapper.writeValueAsString(photography);

        mockMvc.perform(post("/schedule/create")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetScheduleById() throws Exception {
        Schedule photography = new Schedule();
        photography.setId(1L);
        photography.setTotal_amount(100.0);
        photography.setSchedule_amount(50.0);
        photography.setPayment_due_date(LocalDate.of(2024, 8, 28));
        photography.setIs_paid(true);
        photography.setPayment_date(LocalDate.of(2024, 8, 28));
        photography.setProject_id(101L);

        given(service.getScheduleById(1L)).willReturn(photography);

        mockMvc.perform(get("/schedule/view/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total_amount").value(100.0))
                .andExpect(jsonPath("$.schedule_amount").value(50.0))
                .andExpect(jsonPath("$.payment_due_date").value("2024-08-28"))
                .andExpect(jsonPath("$.is_paid").value(true))
                .andExpect(jsonPath("$.project_id").value(101));
    }
    
    @Test
    public void testGetAllSchedules() throws Exception {
        Schedule photography = new Schedule();
        photography.setTotal_amount(100.0);
        photography.setSchedule_amount(50.0);
        photography.setPayment_due_date(LocalDate.of(2024, 8, 28));
        photography.setIs_paid(true);
        photography.setPayment_date(LocalDate.of(2024, 8, 28));
        photography.setProject_id(101L);

        given(service.findAllSchedules()).willReturn(List.of(photography));

        mockMvc.perform(get("/schedule/view/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].total_amount").value(100.0))
                .andExpect(jsonPath("$[0].schedule_amount").value(50.0))
                .andExpect(jsonPath("$[0].payment_due_date").value("2024-08-28"))
                .andExpect(jsonPath("$[0].is_paid").value(true))
                .andExpect(jsonPath("$[0].project_id").value(101));
    }

    @Test
    public void testDeleteScheduleById() throws Exception {
        mockMvc.perform(delete("/schedule/delete/1"))
                .andExpect(status().isNoContent());
        then(service).should().deleteScheduleById(1L);
    }
   
    
    // @Test
    // public void testUpdatePaymentSchedule() throws Exception {
    //     // Sample Schedule data
    //     Long scheduleId = 1L;
    //     // Schedule existingSchedule = new Schedule(scheduleId, 10000.0, 5000.0, LocalDate.of(2024, 9, 1), false, null, 101L);
    //     Schedule updatedSchedule = new Schedule(scheduleId, 10000.0, 5000.0, LocalDate.of(2024, 9, 1), true, LocalDate.of(2024, 9, 1), 101L);

    //     // Mock the service call
    //     when(service.updatePaymentSchedule(eq(scheduleId), any(Schedule.class))).thenReturn(updatedSchedule);

    //     // Perform the PUT request
    //     mockMvc.perform(put("/edit/{id}", scheduleId)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(updatedSchedule)))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.id", is(scheduleId.intValue())))
    //             .andExpect(jsonPath("$.total_amount", is(updatedSchedule.getTotal_amount())))
    //             .andExpect(jsonPath("$.schedule_amount", is(updatedSchedule.getSchedule_amount())))
    //             .andExpect(jsonPath("$.payment_due_date", is(updatedSchedule.getPayment_due_date().toString())))
    //             .andExpect(jsonPath("$.is_paid", is(updatedSchedule.getIs_paid())))
    //             .andExpect(jsonPath("$.payment_date", is(updatedSchedule.getPayment_date().toString())))
    //             .andExpect(jsonPath("$.project_id", is(updatedSchedule.getProject_id().intValue())));
    // }

    // @Test
    // public void testUpdatePaymentSchedule() throws Exception {
    //     Schedule updatedSchedule = new Schedule();
    //     updatedSchedule.setTotal_amount(150.0);
    //     updatedSchedule.setSchedule_amount(75.0);
    //     updatedSchedule.setPayment_due_date(LocalDate.of(2024, 11, 28));
    //     updatedSchedule.setIs_paid(false);
    //     updatedSchedule.setPayment_date(LocalDate.of(2024, 11, 28));
    //     updatedSchedule.setProject_id(101L);
    
    //     ObjectMapper mapper = new ObjectMapper();
    //     mapper.registerModule(new JavaTimeModule());
    //     String json = mapper.writeValueAsString(updatedSchedule);
    //     given(service.updatePaymentSchedule(4L, updatedSchedule)).willReturn(updatedSchedule);

    //     mockMvc.perform(put("/schedule/edit/4")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(json))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.total_amount").value(150.0))
    //             .andExpect(jsonPath("$.schedule_amount").value(75.0))
    //             .andExpect(jsonPath("$.payment_due_date").value("2024-11-28"))
    //             .andExpect(jsonPath("$.is_paid").value(false))
    //             .andExpect(jsonPath("$.payment_date").value("2024-11-28"))
    //             .andExpect(jsonPath("$.project_id").value(101));
    // }
    
     // @Test
    // public void testUpdatePaymentSchedule() throws Exception {
    //     Schedule updatedSchedule = new Schedule();
    //     updatedSchedule.setTotal_amount(150.0);
    //     updatedSchedule.setSchedule_amount(75.0);
    //     updatedSchedule.setPayment_due_date(LocalDate.of(2024, 11, 28));
    //     updatedSchedule.setIs_paid(false);
    //     updatedSchedule.setPayment_date(LocalDate.of(2024, 11, 28));
    //     updatedSchedule.setProject_id(101L);
    
    //     given(service.updatePaymentSchedule(4L, updatedSchedule)).willReturn(updatedSchedule);
    
    //     String json = objectMapper.writeValueAsString(updatedSchedule);
    
    //     mockMvc.perform(put("/schedule/edit/4")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(json))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.total_amount").value(150.0))
    //             .andExpect(jsonPath("$.schedule_amount").value(75.0))
    //             .andExpect(jsonPath("$.payment_due_date").value("2024-11-28"))
    //             .andExpect(jsonPath("$.is_paid").value(false))
    //             .andExpect(jsonPath("$.payment_date").value("2024-11-28"))
    //             .andExpect(jsonPath("$.project_id").value(101L));
    // }

    // @Test
    // public void testUpdatePaymentSchedule() throws Exception {
    //     Schedule updatedSchedule = new Schedule();
    //     updatedSchedule.setTotal_amount(150.0);
    //     updatedSchedule.setSchedule_amount(75.0);
    //     updatedSchedule.setPayment_due_date(LocalDate.of(2024, 11, 28));
    //     updatedSchedule.setIs_paid(false);
    //     updatedSchedule.setPayment_date(LocalDate.of(2024, 11, 28));
    //     updatedSchedule.setProject_id(101L);
    
    //     given(service.updatePaymentSchedule(4L, updatedSchedule)).willReturn(updatedSchedule);
    
    //     String json = objectMapper.writeValueAsString(updatedSchedule);
    
    //     mockMvc.perform(put("/schedule/edit/4")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(json))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.total_amount").value(150.0))
    //             .andExpect(jsonPath("$.schedule_amount").value(75.0))
    //             .andExpect(jsonPath("$.payment_due_date").value("2024-11-28"))
    //             .andExpect(jsonPath("$.is_paid").value(false))
    //             .andExpect(jsonPath("$.payment_date").value("2024-11-28"))
    //             .andExpect(jsonPath("$.project_id").value(101L));
      
    //     // Schedule updatedSchedule = new Schedule();
    //     // updatedSchedule.setTotal_amount(150.0);
    //     // updatedSchedule.setSchedule_amount(75.0);
    //     // updatedSchedule.setPayment_due_date(LocalDate.of(2024, 11, 28));
    //     // updatedSchedule.setIs_paid(false);
    //     // updatedSchedule.setPayment_date(LocalDate.of(2024, 11, 28));
    //     // updatedSchedule.setProject_id(101L);

    //     // given(service.updatePaymentSchedule(4L, updatedSchedule)).willReturn(updatedSchedule);

    //     // mockMvc.perform(put("/schedule/edit/4"))
        
    //     // .andExpect(status().isOk())
    //     // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //     // .andExpect(jsonPath("$.total_amount").value(150.0))
    //     // .andExpect(jsonPath("$.schedule_amount").value(75.0))
    //     // .andExpect(jsonPath("$.payment_due_date").value("2024-11-28"))
    //     // .andExpect(jsonPath("$.is_paid").value(false))
    //     // .andExpect(jsonPath("$.payment_date").value("2024-11-28"))
    //     // .andExpect(jsonPath("$.project_id").value(101L));
       
    // }

    //  mockMvc.perform(put("/schedule/edit/1")
    //     .contentType(MediaType.APPLICATION_JSON)
    //     .content(asJsonString(updatedSchedule))) 
    //     .andExpect(status().isOk())
    //     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //     .andExpect(jsonPath("$.total_amount").value(150.0))
    //     .andExpect(jsonPath("$.schedule_amount").value(75.0))
    //     .andExpect(jsonPath("$.payment_due_date").value("2024-08-28"))
    //     .andExpect(jsonPath("$.is_paid").value(false))
    //     .andExpect(jsonPath("$.project_id").value(101L));
}


// import java.time.LocalDate;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.payment.schedule.controller.PhotographyController;
// import com.payment.schedule.model.Photography;
// import com.payment.schedule.service.PhotographyService;

// @WebMvcTest(PhotographyController.class)
// public class PhotographyControllerTest {

//    private MockMvc mockMvc;

//     @Mock
//     private PhotographyService service;

//     @InjectMocks
//     private PhotographyController controller;

//     @BeforeEach
//     public void setup() {
//         MockitoAnnotations.openMocks(this);
//         mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//     }

//    @Test
//     public void testAddEvent() throws Exception {
//         Photography photography = new Photography();
//         photography.setTotal_amount(100.0);
//         photography.setSchedule_amount(50.0);
//         photography.setPayment_due_date(LocalDate.of(2024, 8, 28));  // Using LocalDate
//         photography.setIs_paid(true);
//         photography.setProject_id(1L);

//         ObjectMapper objectMapper = new ObjectMapper();
//         String json = objectMapper.writeValueAsString(photography);

//         mockMvc.perform(post("/photography")
//                 .contentType("application/json")
//                 .content(json))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.payment_due_date", is("2024-08-28")));  // Check if the date is serialized correctly
//     }

    // @Test
    // public void testGetEventById() throws Exception {
    //     Photography photography = new Photography();
    //     photography.setId(1L);
    //     photography.setTotal_amount(100.0);
    //     photography.setSchedule_amount(50.0);
    //     photography.setPayment_due_date(LocalDate.of(2024, 8, 28));
    //     photography.setIs_paid(true);
    //     photography.setProject_id(1L);

    //     given(service.getEventById(1L)).willReturn(photography);

    //     mockMvc.perform(get("/schedule/view/1"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.total_amount").value(100.0))
    //             .andExpect(jsonPath("$.schedule_amount").value(50.0))
    //             .andExpect(jsonPath("$.payment_due_date").value("2024-08-28"))
    //             .andExpect(jsonPath("$.is_paid").value(true))
    //             .andExpect(jsonPath("$.project_id").value(1));
    // }

    // @Test
    // public void testGetAllEvents() throws Exception {
    //     Photography photography = new Photography();
    //     photography.setTotal_amount(100.0);
    //     photography.setSchedule_amount(50.0);
    //     photography.setPayment_due_date("2024-08-28");
    //     photography.setIs_paid(true);
    //     photography.setProject_id(1L);

    //     given(service.findAllEvents()).willReturn(List.of(photography));

    //     mockMvc.perform(get("/schedule/view/all"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$[0].total_amount").value(100.0))
    //             .andExpect(jsonPath("$[0].schedule_amount").value(50.0))
    //             .andExpect(jsonPath("$[0].payment_due_date").value("2024-08-28"))
    //             .andExpect(jsonPath("$[0].is_paid").value(true))
    //             .andExpect(jsonPath("$[0].project_id").value(1));
    // }

    // @Test
    // public void testDeleteEventById() throws Exception {
    //     mockMvc.perform(delete("/schedule/delete/1"))
    //             .andExpect(status().isNoContent());

    //     // Verify the delete method was called
    //     then(service).should().deleteEventById(1L);
    // }

    // @Test
    // public void testUpdatePaymentSchedule() throws Exception {
    //     Photography existingSchedule = new Photography();
    //     existingSchedule.setId(1L);
    //     existingSchedule.setTotal_amount(100.0);
    //     existingSchedule.setSchedule_amount(50.0);
    //     existingSchedule.setPayment_due_date("2024-08-28");
    //     existingSchedule.setIs_paid(true);
    //     existingSchedule.setProject_id(1L);

    //     Photography updatedSchedule = new Photography();
    //     updatedSchedule.setTotal_amount(150.0);
    //     updatedSchedule.setSchedule_amount(75.0);
    //     updatedSchedule.setPayment_due_date("2024-09-01");
    //     updatedSchedule.setIs_paid(false);
    //     updatedSchedule.setProject_id(2L);

    //     given(service.updatePaymentSchedule(1L, updatedSchedule)).willReturn(updatedSchedule);

    //     mockMvc.perform(put("/schedule/edit/1")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("{\"total_amount\":150.0,\"schedule_amount\":75.0,\"payment_due_date\":\"2024-09-01\",\"is_paid\":false,\"project_id\":2}"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.total_amount").value(150.0))
    //             .andExpect(jsonPath("$.schedule_amount").value(75.0))
    //             .andExpect(jsonPath("$.payment_due_date").value("2024-09-01"))
    //             .andExpect(jsonPath("$.is_paid").value(false))
    //             .andExpect(jsonPath("$.project_id").value(2));
    // }
// }

