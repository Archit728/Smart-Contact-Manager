package com.scm.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "user")
/*  Marks a class as an entity, which means it is mapped to a table in a relational database. 
This annotation is mandatory for any class we want to be persisted in a database using JPA.*/
@Table(name = "users")
/*Specifies the details of the table that the entity will be mapped to in the database. 
It allows customization of the table name, schema, and unique constraints. Optional Annotation*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails { //making the class as UserDetails for spring security as it uses UserDetails to represent user so if we return email in username method by spring security it will understand it as username

  @Id
  private String userId;

  @Column(name = "user_name", nullable = false) //customizing the specific columns their name and properties
  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  private String password;

  @Lob
  @Column(length = 10000)
  private String about;

  @Lob
  @Column(length = 10000)
  private String profilePic;

  private String phoneNumber;

  //information
  @Builder.Default
  private boolean enabled = false; //if enabled user can login or else won't and spring security will be used to authenticate this

  @Builder.Default
  private boolean emailVerified = false;

  @Builder.Default
  private boolean phoneVerified = false;

  @Enumerated(value = EnumType.STRING)
  //how the user signed up- self, google, facebook, twitter, linkedIn, github(by which way the user singed up)
  @Builder.Default
  private Providers provider = Providers.SELF;

  private String providerUserId;

  //add more fields if needed
  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    orphanRemoval = true
  )
  /*cascade used ->if user gets deleted delete all its contacts
   * The related entity or collection is fetched only when it is explicitly accessed for the first time. Until then, it is not loaded from the database.
   * any child entity that is removed from the relationship will be automatically deleted from the database. => orphanRemoval
   * useful when you have a parent-child relationship, and when a child entity is no longer associated with the parent, you want the child to be deleted automatically.
   * Without orphanRemoval, the child entity would stay in the database unless explicitly removed.
   * The mappedBy attribute is used to define the inverse side of a bidirectional relationship. It tells Hibernate that the relationship is already being managed by the other side (the "owning side").
   * In a bidirectional relationship, one entity owns the relationship (the owning side), and the other is the inverse side (the non-owning side).
   * The mappedBy attribute is used on the inverse side to indicate which field or property is responsible for managing the relationship.
   */
  @Builder.Default
  private List<Contact> contacts = new ArrayList<>();

  //defining roles for each user
  @Builder.Default
  @ElementCollection(fetch = FetchType.EAGER)
  List<String> roleList = new ArrayList<>();

  private String emailVerificationToken;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // list of roles[USER, ADMIN] => collection of SimpleGrantedAuthority{ADMIN,USER}
    Collection<SimpleGrantedAuthority> roles = roleList
      .stream()
      .map(role -> new SimpleGrantedAuthority(role))
      .collect(Collectors.toList());
    return roles;
  }

  //email = username
  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
}
