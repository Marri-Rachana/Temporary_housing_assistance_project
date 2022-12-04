package com.housebooking.app.test.Services;

import com.housebooking.app.dao.*;
import com.housebooking.app.model.*;
import com.housebooking.app.service.HomeService;
import com.housebooking.app.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeServiceTest {

    @InjectMocks
    HomeService homeService;

    @Mock
    private HomeRepo homeRepo;
    @Mock
    private UserProfileRepo userProfileRepo;
    @Mock
    private UserSecurityRepo userSecurityRepo;
    @Mock
    private AddressRepo addressRepo;
    @Mock
    private ReviewRepo reviewsRepo;
    @Mock
    private AnnouncementRepo announcementRepo;

    @Test
    public void saveUserTest(){
        when(homeRepo.save(any())).thenReturn(TestUtils.getUserModel());
        int i = homeService.saveUser(TestUtils.getUserModel());
        assertEquals(1,i,"i should be 1");
    }

    @Test
    public void saveUserTest_negative(){
        when(homeRepo.save(any())).thenReturn(null);
        int i = homeService.saveUser(TestUtils.getUserModel());
        assertEquals(0,i,"i should be 0");
    }


    @Test
    public void authenticateUser_Test(){
        List<UserModel> userList = new ArrayList<>();
        UserModel user = new UserModel();
        user.setId(8l);
        user.setEmail("temp.house@test.com");
        user.setUsername("temp house");
        user.setPassword("temp");
        user.setUsertype("student");
        userList.add(user);
        when(homeRepo.findAll()).thenReturn(userList);
        UserModel userModel = homeService.authenticateUser(user);
        assertNotNull(userModel,"UserModel should not be null");

        UserModel userModel1 = new UserModel();
        userModel1.setEmail("admin@gmail.com");
        userModel1.setPassword("admin");
        userModel1 = homeService.authenticateUser(userModel1);
        assertEquals("admin",userModel1.getUsertype(),"Usertype should be admin");

        userModel1.setEmail("abc@gmail.com");
        userModel1.setUsername("abc");
        userModel1 = homeService.authenticateUser(userModel1);
        assertNull(userModel1);
    }

    @Test
    public void authenticateUser_negativeTest(){
        List<UserModel> userList = new ArrayList<>();
        UserModel user = new UserModel();
        user.setId(8l);
        user.setEmail("abc@gmail.com");
        user.setUsername("abc");
        user.setPassword("temp");
        user.setUsertype("student");
        userList.add(user);
        when(homeRepo.findAll()).thenReturn(userList);
        UserModel userModel1 = new UserModel();
        userModel1.setEmail("abc@gmail.com");
        userModel1.setUsername("abc");
        userModel1 = homeService.authenticateUser(userModel1);
        assertNull(userModel1);
    }

    @Test
    public void validatePasswordTest(){
        List<UserModel> userList = new ArrayList<>();
        userList.add(TestUtils.getUserModel());
        when(homeRepo.findAll()).thenReturn(userList);
        List<UserSecurityModel> userSecurityModels = new ArrayList<>();
        userSecurityModels.add(TestUtils.getUserSecurityModel());
        when(userSecurityRepo.findAll()).thenReturn(userSecurityModels);
        int i = homeService.validatePassword(TestUtils.getUserModel(), "Graduation college", "Gurunanak engineering college");
        assertEquals(1,i,"i should be 1");

        i = homeService.validatePassword(TestUtils.getUserModel(), "Graduation college", "CBIT");
        assertEquals(2,i,"i should be 2");

        UserModel userModel = TestUtils.getUserModel();
        userModel.setEmail("admin");
        i = homeService.validatePassword(userModel, "Graduation college", "CBIT");
        assertEquals(0,i,"i should be 0");
    }

    @Test
    public void saveNewPasswordTest(){
        List<UserModel> userList = new ArrayList<>();
        userList.add(TestUtils.getUserModel());
        when(homeRepo.findbyEmail(any())).thenReturn(TestUtils.getUserModel());
        int result = homeService.saveNewPassword(TestUtils.getUserModel());
        verify(homeRepo,times(1)).save(any());
    }

    @Test
    public void saveNewPasswordTest_negativeTest(){
        when(homeRepo.findbyEmail(any())).thenThrow(NullPointerException.class);
        int result = homeService.saveNewPassword(TestUtils.getUserModel());
        assertEquals(0,result);

    }

    @Test
    public void findUserTest(){
        List<UserModel> userList = new ArrayList<>();
        userList.add(TestUtils.getUserModel());
        when(homeRepo.findAll()).thenReturn(userList);
        UserModel user = homeService.findUser("rachana@gmail.com");
        assertNotNull(user,"user should not be null");
    }

    @Test
    public void findUserTest_negativeTest() {
        when(homeRepo.findAll()).thenReturn(Collections.emptyList());
        UserModel userModel = homeService.findUser("rachana@gmail.com");
        assertNull(userModel, "user should be null");

        List<UserModel> userList = new ArrayList<>();
        userList.add(TestUtils.getUserModel());
        when(homeRepo.findAll()).thenReturn(userList);

        when(homeRepo.findAll()).thenReturn(userList);
        UserModel userModel1 = homeService.findUser("abc@gmail.com");
        assertNull(userModel1, "user should be null");
    }

    @Test
    public void findUserByUsernameTest(){
        List<UserModel> userList = new ArrayList<>();
        userList.add(TestUtils.getUserModel());
        when(homeRepo.findAll()).thenReturn(userList);
        UserModel user = homeService.findUserByUsername("rachana");
        assertNotNull(user,"user should not be null");
    }

    @Test
    public void findUserByUsernameTest_negativeTest() {
        List<UserModel> userList = new ArrayList<>();
        userList.add(TestUtils.getUserModel());
        when(homeRepo.findAll()).thenReturn(userList);
        UserModel user = homeService.findUserByUsername("abc");
        assertNull(user, "user should be null");
    }

    @Test
    public void deleteUserTest(){
        homeService.deleteUser(1L);
        verify(homeRepo,times(1)).deleteById(any());
        verify(userProfileRepo,times(1)).deleteById(any());
        verify(userSecurityRepo,times(1)).deleteById(any());
        verify(addressRepo,times(1)).deleteByUserProfile(any());
    }

    @Test
    public void deleteUserTest_negativeCase(){
        UserModel modeltest = TestUtils.getUserModel();
        modeltest.setId(3l);
        Mockito.doThrow(NullPointerException.class).when(homeRepo).deleteById(3l);
        int result = homeService.deleteUser(3l);
        assertEquals(0,result);
    }

    @Test
    public void getAllStudentsTest(){
        List<UserModel> userList = new ArrayList<>();
        userList.add(TestUtils.getUserModel());
        when(homeRepo.findAllStudents()).thenReturn(userList);
        List<UserModel> allStudents = homeService.getAllStudents();
        assertEquals(userList,allStudents);
    }

    @Test
    public void getAllStudentsTest_negativeTest(){
        Mockito.doThrow(NullPointerException.class).when(homeRepo).findAllStudents();
        List<UserModel> allStudents = homeService.getAllStudents();
        assertEquals(null,allStudents);
    }

    @Test
    public void getAllOwnersTest(){
        List<UserModel> userList = new ArrayList<>();
        userList.add(TestUtils.getUserModel());
        when(homeRepo.findAllOwners()).thenReturn(userList);
        List<UserModel> allStudents = homeService.getAllOwners();
        assertEquals(1,allStudents.size(),"allStudents size should be 1");
    }

    @Test
    public void getAllOwnersTest_negativeTest(){
        Mockito.doThrow(NullPointerException.class).when(homeRepo).findAllOwners();
        List<UserModel> allStudents = homeService.getAllOwners();
        assertEquals(null,allStudents);
    }

    @Test
    public void saveUserProfileTest(){
        when(homeRepo.findbyEmail(any())).thenReturn(TestUtils.getUserModel());
        when(userProfileRepo.save(any())).thenReturn(TestUtils.getUserProfileModel());
        homeService.saveUserProfile("123","rachana","marri","22",
                "rachana.marri@gmail.com","123","abc","Hyd","5000039");
        verify(addressRepo,times(1)).save(any());
    }

    @Test
    public void saveUserProfileTest_negativeTest(){
        when(homeRepo.findbyEmail(any())).thenThrow(NullPointerException.class);
        int result = homeService.saveUserProfile("123","rachana","marri","22",
                "rachana.marri@gmail.com","123","abc","Hyd","5000039");
        assertEquals(0,result);
    }

    @Test
    public void getAllAnnouncementsTest(){
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(TestUtils.getAnnouncement());
        when(announcementRepo.findAll()).thenReturn(announcements);
        List<Announcement> allAnnouncements = homeService.getAllAnnouncements();
        assertEquals(1,allAnnouncements.size(),"allAnnouncements size should be 1");
    }

    @Test
    public void getAllAnnouncementsTest_negativeTest(){
        when(announcementRepo.findAll()).thenThrow(NullPointerException.class);
        List<Announcement> allAnnouncements = homeService.getAllAnnouncements();
        assertEquals(null,allAnnouncements);
    }

    @Test
    public void saveUserSecurityTest(){
        when(homeRepo.findbyEmail(any())).thenReturn(TestUtils.getUserModel());
        homeService.saveUserSecurity("college","ace","ace@gmail.com");
        verify(userSecurityRepo,times(1)).save(any());
    }

    @Test
    public void saveUserSecurityTest_negativeTest(){
        when(homeRepo.findbyEmail(any())).thenThrow(NullPointerException.class);
        int result = homeService.saveUserSecurity("college","ace","ace@gmail.com");
        assertEquals(0,result);
    }

    @Test
    public void getUserProfileTest(){
        when(userProfileRepo.findUserProfile(any())).thenReturn(TestUtils.getUserProfileModel());
        UserProfileModel userProfile = homeService.getUserProfile(1L);
        assertNotNull(userProfile,"userProfile should not be null");
    }

    @Test
    public void getUserProfileTest_negativeTest(){
        when(userProfileRepo.findUserProfile(any())).thenThrow(NullPointerException.class);
        UserProfileModel userProfile = homeService.getUserProfile(1L);
        assertEquals(null,userProfile);
    }

    @Test
    public void getUserSecurityTest(){
        when(userSecurityRepo.findUserSecurity(any())).thenReturn(TestUtils.getUserSecurityModel());
        UserSecurityModel userSecurity = homeService.getUserSecurity(1L);
        assertNotNull(userSecurity,"userSecurity should not be null");
    }

    @Test
    public void getUserSecurityTest_negativeTest(){
        when(userSecurityRepo.findUserSecurity(any())).thenThrow(NullPointerException.class);
        UserSecurityModel userSecurity = homeService.getUserSecurity(1L);
        assertEquals(null,userSecurity);
    }

    @Test
    public void getUserAddressTest(){
        when(addressRepo.findUserAddress(any())).thenReturn(TestUtils.getAddressModel());
        AddressModel addressModel = homeService.getUserAddress(1L);
        assertNotNull(addressModel,"addressModel should not be null");
    }

    @Test
    public void getUserAddressTest_negtiveTest(){
        when(addressRepo.findUserAddress(any())).thenThrow(NullPointerException.class);
        AddressModel addressModel = homeService.getUserAddress(1L);
        assertEquals(null,addressModel);
    }

    @Test
    public void updateUserProfileTest(){
        when(userProfileRepo.findUserProfile(any())).thenReturn(TestUtils.getUserProfileModel());
        homeService.updateUserProfile(1L,"abc@gmail.com","123445","rachana","marri","21");
        verify(userProfileRepo,times(1)).save(any());
    }

    @Test
    public void updateUserProfileTest_negativeTest(){
        when(userProfileRepo.findUserProfile(any())).thenThrow(NullPointerException.class);
        int result= homeService.updateUserProfile(1L,"abc@gmail.com","123445","rachana","marri","21");
        assertEquals(0,result);
    }

    @Test
    public void updateUserSecurityTest(){
        when(userSecurityRepo.findUserSecurity(any())).thenReturn(TestUtils.getUserSecurityModel());
        homeService.updateUserSecurity(1L,"college","abc");
        verify(userSecurityRepo,times(1)).save(any());
    }

    @Test
    public void updateUserSecurityTest_negativeTest(){
        when(userSecurityRepo.findUserSecurity(any())).thenThrow(NullPointerException.class);
        int result = homeService.updateUserSecurity(1L,"college","abc");
        assertEquals(0, result);
    }

    @Test
    public void updateUserAddressTest(){
        when(addressRepo.findUserAddress(any())).thenReturn(TestUtils.getAddressModel());
        homeService.updateUserAddress(1L,"123","abc","hyd","500039");
        verify(addressRepo,times(1)).save(any());
    }

    @Test
    public void updateUserAddressTest_negativeTest(){
        when(addressRepo.findUserAddress(any())).thenThrow(NullPointerException.class);
        int result = homeService.updateUserAddress(1L,"123","abc","hyd","500039");
        assertEquals(0, result);
    }

    @Test
    public void getAllReviewsTest(){
        List<ReviewModel> review = new ArrayList<>();
        review.add(TestUtils.getReviewModel());
        when(reviewsRepo.findAll()).thenReturn(review);
        List<ReviewModel> allReviews = homeService.getAllReviews();
        assertNotNull(allReviews,"allReviews should not be null");
    }
    @Test
    public void getAllReviewsTest_negativeTest(){
        when(reviewsRepo.findAll()).thenThrow(NullPointerException.class);
        List<ReviewModel> allReviews = homeService.getAllReviews();
        assertEquals(null,allReviews);
    }

    @Test
    public void saveUserAddressTest(){
        when( homeRepo.findbyEmail(any())).thenReturn(TestUtils.getUserModel());
        int result = homeService.saveUserAddress("1","123","abc","23","test@test.test");
        assertEquals(1,result);
    }

    @Test
    public void saveUserAddress_negativeTest(){
        when(homeRepo.findbyEmail(any())).thenThrow(NullPointerException.class);
        int result = homeService.saveUserAddress("1","123","abc","23","test@test.test");
        assertEquals(0,result);
    }

}
