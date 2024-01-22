package com.objects.marketbridge.domain.member.controller;


import com.objects.marketbridge.domain.member.dto.IsCheckedDto;
import com.objects.marketbridge.domain.member.service.MemberService;
import com.objects.marketbridge.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;


class MemberControllerTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);

    @Test
    @DisplayName("이메일 중복체크 API")
//    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_METHOD,value = "member1@example.com")
//    @WithMockUser
    void isDuplicateEmail() throws Exception {
        //given
        String requestEmail  = "user1@example.com";

        given(memberService.isDuplicateEmail(any(String.class)))
                .willReturn(IsCheckedDto.builder().isCheckEmail(true).build());


        //when
        mockMvc.perform(
                        get("/member/check-email")
                                .param("email",requestEmail)
                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(requestEmail))
                )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("check-email",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("email").description("이메일")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.isCheckEmail").type(JsonFieldType.BOOLEAN).description("불린값")
                        )
                ));
    }

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }


}