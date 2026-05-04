package com.nexus.mobility.service;

import com.nexus.mobility.entity.Country;
import com.nexus.mobility.entity.CountryStat;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.NewsItem;
import com.nexus.mobility.entity.Notification;
import com.nexus.mobility.entity.Partnership;
import com.nexus.mobility.entity.Program;
import com.nexus.mobility.entity.Student;
import com.nexus.mobility.entity.Tenant;
import com.nexus.mobility.entity.TenantSettings;
import com.nexus.mobility.entity.UserAccount;
import com.nexus.mobility.repository.CountryRepository;
import com.nexus.mobility.repository.CountryStatRepository;
import com.nexus.mobility.repository.NewsItemRepository;
import com.nexus.mobility.repository.NotificationRepository;
import com.nexus.mobility.repository.PartnershipRepository;
import com.nexus.mobility.repository.ProgramRepository;
import com.nexus.mobility.repository.StudentRepository;
import com.nexus.mobility.repository.TenantRepository;
import com.nexus.mobility.repository.TenantSettingsRepository;
import com.nexus.mobility.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final String DEMO_EMAIL = "demo@nexus.edu";
    private static final String DEMO_PASSWORD = "Demo@123";

    private final TenantRepository tenantRepository;
    private final TenantSettingsRepository tenantSettingsRepository;
    private final UserAccountRepository userAccountRepository;
    private final StudentRepository studentRepository;
    private final ProgramRepository programRepository;
    private final PartnershipRepository partnershipRepository;
    private final CountryRepository countryRepository;
    private final CountryStatRepository countryStatRepository;
    private final NewsItemRepository newsItemRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            TenantRepository tenantRepository,
            TenantSettingsRepository tenantSettingsRepository,
            UserAccountRepository userAccountRepository,
            StudentRepository studentRepository,
            ProgramRepository programRepository,
            PartnershipRepository partnershipRepository,
            CountryRepository countryRepository,
            CountryStatRepository countryStatRepository,
            NewsItemRepository newsItemRepository,
            NotificationRepository notificationRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.tenantRepository = tenantRepository;
        this.tenantSettingsRepository = tenantSettingsRepository;
        this.userAccountRepository = userAccountRepository;
        this.studentRepository = studentRepository;
        this.programRepository = programRepository;
        this.partnershipRepository = partnershipRepository;
        this.countryRepository = countryRepository;
        this.countryStatRepository = countryStatRepository;
        this.newsItemRepository = newsItemRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Tenant existingTenant = tenantRepository.findByCode("NEXUS-U").orElse(null);
        if (existingTenant != null) {
            ensureDemoUser(existingTenant.getId());
            return;
        }

        Tenant tenant = new Tenant();
        tenant.setName("Nexus University");
        tenant.setCode("NEXUS-U");
        tenantRepository.save(tenant);

        TenantSettings settings = new TenantSettings();
        settings.setTenantId(tenant.getId());
        settings.setContactEmail("mobility@nexus.edu");
        tenantSettingsRepository.save(settings);

        UserAccount admin = new UserAccount();
        admin.setTenantId(tenant.getId());
        admin.setFullName("Platform Admin");
        admin.setEmail("admin@nexus.edu");
        admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
        admin.setRole(DomainEnums.UserRole.ADMIN);
        userAccountRepository.save(admin);

        UserAccount demoUser = new UserAccount();
        demoUser.setTenantId(tenant.getId());
        demoUser.setFullName("Client Demo");
        demoUser.setEmail(DEMO_EMAIL);
        demoUser.setPasswordHash(passwordEncoder.encode(DEMO_PASSWORD));
        demoUser.setRole(DomainEnums.UserRole.ADMIN);
        userAccountRepository.save(demoUser);

        seedCountry("IN", "India", "Asia", "India");
        seedCountry("DE", "Germany", "Europe", "Germany");
        seedCountry("SG", "Singapore", "Asia", "Singapore");
        seedCountry("GB", "United Kingdom", "Europe", "United Kingdom");

        Student student = new Student();
        student.setTenantId(tenant.getId());
        student.setFullName("Aarav Sharma");
        student.setEmail("aarav.sharma@nexus.edu");
        student.setHomeUniversity("Nexus University");
        student.setHostUniversity("TU Munich");
        student.setHostCountryCode("DE");
        student.setProgramName("European Exchange Semester");
        student.setSemesterLabel("Fall 2026");
        student.setGpa(new BigDecimal("3.84"));
        student.setStatus("Approved");
        studentRepository.save(student);

        Partnership partnership = new Partnership();
        partnership.setTenantId(tenant.getId());
        partnership.setUniversityName("TU Munich");
        partnership.setCountryCode("DE");
        partnership.setPartnershipType("Academic Exchange");
        partnership.setStatus(DomainEnums.PartnershipStatus.ACTIVE);
        partnership.setStartDate(LocalDate.of(2024, 1, 1));
        partnership.setExpiryDate(LocalDate.of(2028, 12, 31));
        partnership.setMouSigned(true);
        partnershipRepository.save(partnership);

        Program program = new Program();
        program.setTenantId(tenant.getId());
        program.setPartnershipId(partnership.getId());
        program.setName("European Exchange Semester");
        program.setType(DomainEnums.ProgramType.SEMESTER_EXCHANGE);
        program.setPartnerUniversity("TU Munich");
        program.setCountryCode("DE");
        program.setSeats(20);
        program.setEnrolled(12);
        program.setDeadline(LocalDate.now().plusMonths(1));
        program.setDurationLabel("1 Semester");
        program.setScholarshipAvailable(true);
        programRepository.save(program);

        CountryStat stat = new CountryStat();
        stat.setTenantId(tenant.getId());
        stat.setCountryCode("DE");
        stat.setSnapshotYear(LocalDate.now().getYear());
        stat.setOutboundStudents(42);
        stat.setInboundStudents(17);
        stat.setPartnershipCount(4);
        countryStatRepository.save(stat);

        NewsItem news = new NewsItem();
        news.setTenantId(tenant.getId());
        news.setTitle("Mobility scholarship window opens");
        news.setCategory(DomainEnums.NewsCategory.SCHOLARSHIP);
        news.setPublishDate(LocalDate.now());
        news.setSummary("Applications are now open for the 2026 mobility scholarship cycle.");
        newsItemRepository.save(news);

        Notification notification = new Notification();
        notification.setUserId(demoUser.getId());
        notification.setTitle("Seed data ready");
        notification.setBody("Use demo@nexus.edu / Demo@123 for the client showcase.");
        notificationRepository.save(notification);
    }

    private void ensureDemoUser(java.util.UUID tenantId) {
        userAccountRepository.findByEmail(DEMO_EMAIL).ifPresentOrElse(existingUser -> {
            existingUser.setActive(true);
            existingUser.setTenantId(tenantId);
            existingUser.setFullName("Client Demo");
            existingUser.setPasswordHash(passwordEncoder.encode(DEMO_PASSWORD));
            existingUser.setRole(DomainEnums.UserRole.ADMIN);
            userAccountRepository.save(existingUser);
        }, () -> {
            UserAccount demoUser = new UserAccount();
            demoUser.setTenantId(tenantId);
            demoUser.setFullName("Client Demo");
            demoUser.setEmail(DEMO_EMAIL);
            demoUser.setPasswordHash(passwordEncoder.encode(DEMO_PASSWORD));
            demoUser.setRole(DomainEnums.UserRole.ADMIN);
            userAccountRepository.save(demoUser);
        });
    }

    private void seedCountry(String code, String name, String region, String flag) {
        Country country = new Country();
        country.setCode(code);
        country.setName(name);
        country.setRegion(region);
        country.setFlagEmoji(flag);
        countryRepository.save(country);
    }
}
