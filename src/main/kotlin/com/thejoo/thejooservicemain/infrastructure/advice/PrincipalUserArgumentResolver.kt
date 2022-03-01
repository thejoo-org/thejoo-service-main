package com.thejoo.thejooservicemain.infrastructure.advice

import com.thejoo.thejooservicemain.config.security.nameAsLong
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.infrastructure.annotation.PrincipalUser
import com.thejoo.thejooservicemain.service.UserService
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class PrincipalUserArgumentResolver(
    private val userService: UserService,
): HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(PrincipalUser::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): User {
        if (webRequest.userPrincipal == null)
            throw TheJooException
                .ofBadRequest(ExceptionCode.INVALID_PRINCIPAL, "User can NOT be retrieved from principal")
        return userService.getUserById(webRequest.userPrincipal!!.nameAsLong())
    }
}
