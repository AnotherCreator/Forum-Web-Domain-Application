package org.anothercreator.webapp.domain;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class User {
    public User() {}

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
        this.dateCreated = LocalDate.now();
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank @Size(min = 2, max = 20)
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @FutureOrPresent
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;

        if (that == null || getClass() != that.getClass()) return false;
        User member = (User) that;

        // In a 'GeneratedValue' scenario, as a rule we can not check for persistence equality if either ID is null
        if ((this.ID == null || member.ID == null)) {
            return false;
        }

        return ID.equals(member.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
