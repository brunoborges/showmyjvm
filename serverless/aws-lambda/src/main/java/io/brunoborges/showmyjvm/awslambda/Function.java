package io.brunoborges.showmyjvm.awslambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.brunoborges.showmyjvm.core.ShowJVM;

public class Function implements RequestHandler<Integer, String> {

        @Override
        public String handleRequest(Integer input, Context context) {
                context.getLogger().log("Request in. Returning JVM details...");
                return new ShowJVM().dumpJVMDetails();
        }

}
