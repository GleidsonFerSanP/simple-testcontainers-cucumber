package com.fersanp.gleidson.features;

import org.mockito.internal.exceptions.Reporter;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.invocation.Invocation;
import org.mockito.verification.VerificationMode;

public class ExceptionThrowVerificationMode implements VerificationMode {

    private final Class<? extends Throwable> exceptionClass;

    public ExceptionThrowVerificationMode(Class<? extends Throwable> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    @Override
    public void verify(VerificationData verificationData) {
        Invocation invocation = verificationData.getAllInvocations().get(0);
        try {
            invocation.callRealMethod();
        } catch (Throwable e) {
           if(!e.getClass().getName().equals(exceptionClass.getName())){
               Reporter.checkedExceptionInvalid(e);
           }
        }
    }
}
