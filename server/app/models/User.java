package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends Model {
    @Id
    @GeneratedValue
    public Long id;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Column(length = 64, nullable = false)
    public String password;

//    @Column(length = 64, nullable = false)
//    private byte[] shaPassword;

    @Column(length = 255, nullable = false)
    @Constraints.Required
    public String firstName;

    @Column(length = 255, nullable = false)
    @Constraints.Required
    public String lastName;

//    public void setPassword(String password) {
//        this.shaPassword = getSha512(password);
//    }

    // TODO Consider adding validation function.

    public static final Finder<Long, User> find = new Finder<>(User.class);

    public static User authenticate(String email, String password) {
        return find.where().eq("email", email).eq("password", password).findUnique();
    }

    public static User findByEmail(String email) {
        return find
                .where()
                .eq("email", email.toLowerCase())
                .findUnique();
    }

//    public static byte[] getSha512(String value) {
//        try {
//            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
//        }
//        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
