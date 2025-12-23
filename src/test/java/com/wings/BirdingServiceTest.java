package com.wings;

import com.wings.models.Bird;
import com.wings.models.SpottedBird;
import com.wings.models.User;
import com.wings.repository.BirdRepository;
import com.wings.repository.FriendshipRepository;
import com.wings.repository.SpottedBirdRepository;
import com.wings.repository.UserRepository;
import com.wings.service.BirdingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BirdingServiceTest {
    private BirdingService birdingService;
    private SpottedBirdRepository mockSpottedBirdRepo;
    private BirdRepository mockBirdRepo;
    private UserRepository mockUserRepo;
    private FriendshipRepository mockFriendshipRepo;

    @BeforeEach
    public void setUp() {
        mockSpottedBirdRepo = mock(SpottedBirdRepository.class);
        mockBirdRepo = mock(BirdRepository.class);
        mockUserRepo = mock(UserRepository.class);
        mockFriendshipRepo = mock(FriendshipRepository.class);  
        birdingService = new BirdingService(mockUserRepo, mockBirdRepo, mockSpottedBirdRepo, null);
    }

    // CREATE USER TESTS
    // TEST 1
    @Test
    @DisplayName("Should create user successfully with valid username")
    public void testCreateUserSuccess() throws SQLException {
        String username = "username";
        when(mockUserRepo.getUserByUsername(username)).thenReturn(null);
        
        User createdUser = birdingService.createUser(username);

        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());
        assertEquals(0, createdUser.getCurrentStreak());

        verify(mockUserRepo).saveUser(any(User.class));
    }

    // TEST 2
    @Test
    @DisplayName("Should fail to create user with duplicate username")
    public void testCreateUserDuplicateUsernameFail() throws SQLException {
        String username = "username";
        User existingUser = new User(UUID.randomUUID(), 0, username);
        when(mockUserRepo.getUserByUsername(username)).thenReturn(existingUser);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> birdingService.createUser(username));

        assertEquals("Username already exists", exception.getMessage());
        verify(mockUserRepo, never()).saveUser(any(User.class));
    }

    // TEST 3
    @Test
    @DisplayName("Should fail to create user with null username")
    public void testCreateUserNullUsernameFail() throws SQLException {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> birdingService.createUser(null));

        assertEquals("Username cannot be empty", exception.getMessage());
        verify(mockUserRepo, never()).saveUser(any(User.class));
    }

    // TEST 4
    @Test
    @DisplayName("Should fail to create user with empty username")
    public void testCreateUserEmptyUsernameFail() throws SQLException {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> birdingService.createUser(""));

        assertEquals("Username cannot be empty", exception.getMessage());
        verify(mockUserRepo, never()).saveUser(any(User.class));
    }

    // SPOT BIRD TESTS
    // TEST 5
    @Test
    @DisplayName("Should create bird spotting with correct user and bird")
    public void testSpotBirdSuccess() throws SQLException {
        UUID birdId = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), 0, "username");

        birdingService.spotBird(user, birdId);

        ArgumentCaptor<SpottedBird> captor = ArgumentCaptor.forClass(SpottedBird.class);
        verify(mockSpottedBirdRepo).saveSpottedBird(captor.capture());

        SpottedBird saved = captor.getValue();

        assertEquals(birdId, saved.getBirdId());
        assertEquals(user.getUserId(), saved.getUserId());
    }

    // TEST 6
    @Test
    @DisplayName("Should fail to spot bird with null user")
    public void testSpotBirdNullUserFail() throws SQLException {
        UUID birdId = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> birdingService.spotBird(null, birdId));

        assertEquals("User and birdId cannot be null", exception.getMessage());
        verify(mockSpottedBirdRepo, never()).saveSpottedBird(any());
    }

    // TEST 7
    @Test
    @DisplayName("Should fail to spot bird with null bird ID")
    public void testSpotBirdNullBirdIdFail() throws SQLException {
        User user = new User(UUID.randomUUID(), 0, "username");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> birdingService.spotBird(user, null));

        assertEquals("User and birdId cannot be null", exception.getMessage());
        verify(mockSpottedBirdRepo, never()).saveSpottedBird(any());
    }

    // GET SPOTTED BIRDS TESTS
    // TEST 8
    @Test
    @DisplayName("Should return spotted birds for user")
    public void testGetMyBirdsSuccess() throws SQLException {
        UUID userId = UUID.randomUUID();
        UUID birdId1 = UUID.randomUUID();
        UUID birdId2 = UUID.randomUUID();
        
        User user = new User(userId, 0, "username");
        SpottedBird bird1 = new SpottedBird(UUID.randomUUID(), userId, birdId1, LocalDateTime.now());
        SpottedBird bird2 = new SpottedBird(UUID.randomUUID(), userId, birdId2, LocalDateTime.now());
        
        List<SpottedBird> spottedBirds = new ArrayList<>();
        spottedBirds.add(bird1);
        spottedBirds.add(bird2);
        
        when(mockSpottedBirdRepo.getSpottedBirdsByUserId(userId)).thenReturn(spottedBirds);

        List<SpottedBird> result = birdingService.getMyBirds(user);

        assertEquals(2, result.size());
        assertEquals(birdId1, result.get(0).getBirdId());
        assertEquals(birdId2, result.get(1).getBirdId());
    }

    // TEST 9
    @Test
    @DisplayName("Should return empty list when user has no spotted birds")
    public void testGetMyBirdsEmpty() throws SQLException {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, 0, "username");
        
        when(mockSpottedBirdRepo.getSpottedBirdsByUserId(userId)).thenReturn(new ArrayList<>());

        List<SpottedBird> result = birdingService.getMyBirds(user);

        assertTrue(result.isEmpty());
    }

    // TEST 10
    @Test
    @DisplayName("Should fail to add friend with null user")
    public void testAddFriendNullUser() throws SQLException {
        NullPointerException exception = assertThrows(NullPointerException.class, 
            () -> birdingService.addFriend(null, "user2"));

        assertEquals("Cannot invoke \"com.wings.models.User.getUsername()\" because \"currentUser\" is null", exception.getMessage());
    }

    // TEST 11
    @Test
    @DisplayName("Should fail to add friend with null username")
    public void testAddFriendNullUsername() throws SQLException {
        User user1 = new User(UUID.randomUUID(), 0, "user1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> birdingService.addFriend(user1, null));

        assertEquals("Friend username cannot be empty", exception.getMessage());
    }

    // TEST 12
    @Test
    @DisplayName("Should fail to add yourself as friend")
    public void testAddFriendYourself() throws SQLException {
        User user1 = new User(UUID.randomUUID(), 0, "user1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> birdingService.addFriend(user1, "user1"));

        assertEquals("Username must not be your own", exception.getMessage());
    }

    // TEST 13
    @Test
    @DisplayName("Should fail to add friend that does not exist")
    public void testAddFriendNotFound() throws SQLException {
        User user1 = new User(UUID.randomUUID(), 0, "user1");
        
        when(mockUserRepo.getUserByUsername("nonexistent")).thenReturn(null);

        NullPointerException exception = assertThrows(NullPointerException.class, 
            () -> birdingService.addFriend(user1, "nonexistent"));

        assertEquals("Cannot invoke \"com.wings.models.User.getUserId()\" because \"friend\" is null", exception.getMessage());
    }
}