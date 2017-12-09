package controllers.security;

import controllers.security.LoginController;
import models.users.User;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AuthStaff extends Action.Simple {
    public CompletionStage<Result> call(Http.Context ctx) {


        String id = ctx.session().get("email");
        if (id != null) {
            User u = User.getUserById(id);
            if ("staff".equals(u.getRole())) {


                return delegate.call(ctx);
            }
        }

        ctx.flash().put("error", "Staff Login Required.");
        return CompletableFuture.completedFuture(redirect(routes.LoginController.login()));
    }
}
