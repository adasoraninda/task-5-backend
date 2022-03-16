package com.investree.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investree.demo.controller.TransactionController;
import com.investree.demo.model.Transaction;
import com.investree.demo.model.Users;
import com.investree.demo.utils.ResponseWrapper;
import com.investree.demo.view.TransactionService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(TransactionController.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public class TransactionControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private TransactionService service;

  @MockBean
  private ResponseWrapper wrapper;

  @Test
  public void restTemplateSave() throws Exception {
    // arrange
    Users users1 = new Users();
    users1.setId(1L);
    users1.setUsername("username");
    users1.setPassword("password");
    users1.setIsActive(true);

    Users users2 = new Users();
    users2.setId(2L);
    users2.setUsername("username1");
    users2.setPassword("password1");
    users2.setIsActive(true);

    Transaction tx = new Transaction();
    tx.setId(1L);
    tx.setTenor(1);
    tx.setTotalLoan(new BigDecimal(1000000));
    tx.setPercentInterest(1.0);
    tx.setStatus("belum lunas");
    tx.setBorrowerUser(users1);
    tx.setUserBorrow(users2);

    Map result = new HashMap();
    result.put("id", 1L);
    result.put("tenor", 1);
    result.put("totalPinjaman", 1000000);
    result.put("bungaPersen", 1.0);
    result.put("status", "belum lunas");
    result.put("peminjam", users1);
    result.put("meminjam", users2);

    String jsonResult = new ObjectMapper().writeValueAsString(tx);

    when(service.save(tx)).thenReturn(result);

    when(wrapper.getResult(result)).thenReturn(ResponseEntity.ok(result));

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/v1/transaksi")
        .accept(MediaType.APPLICATION_JSON)
        .content(jsonResult)
        .contentType(MediaType.APPLICATION_JSON);

    // act assert
    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    System.out.println(mvcResult.getResponse().getContentAsString());
  }

}
