<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <style type="text/css"><%@include file="../css/form.css" %></style>
    </head>
    <body>
    <div class="center-form">
        <form method="post" action="signin">
            <fieldset class="fieldset-auto-width">
                <legend>Creation du compte</legend>
                <p style="text-align:center"><i>Vous pouvez creer votre compte via ce formulaire.</i></p><br/>

				<table>
				<tr>
				
	                <td><label for="nom">Adresse email <span class="requis">*</span></label></td>
	                <td><input type="email" id="email" name="email" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" /></td>
	                <td><span class="erreur">${form.erreurs['email']}</span></td>
                </tr>
				<tr>
	                <td><label for="motdepasse">Mot de passe <span class="requis">*</span></label></td>
	                <td><input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" /></td>
	                <td><span class="erreur">${form.erreurs['motdepasse']}</span></td>
                </tr>
                <tr>
	                <td><label for="username">username <span class="requis">*</span></label></td>
	                <td><input type="text" id="username" name="username" value="" size="20" maxlength="20" /></td>
	                <td><span class="erreur">${form.erreurs['username']}</span></td>
                </tr>
                <tr>
                	<td colspan="2"><p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p><td>
                </tr>
                </table>
                <input type="submit" value="Connexion" class="submit" />
            </fieldset>
        </form>
        
        
    </div>
    </body>
</html>