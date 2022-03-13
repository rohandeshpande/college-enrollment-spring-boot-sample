Create Schema CourseEnrollment;

CREATE USER 'enrollmentAppUser'@'localhost' IDENTIFIED BY 'tempPwd123';
GRANT ALL PRIVILEGES ON CourseEnrollment.* To 'enrollmentAppUser'@'localhost';

USE CourseEnrollment;

CREATE TABLE `CourseEnrollment`.`RoleMeta` (
  `RoleId` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(10) NOT NULL,
  `CreatedDate` TIMESTAMP DEFAULT NOW(),
  PRIMARY KEY (`RoleId`)
);

CREATE TABLE `CourseEnrollment`.`User` (
  `UserId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  `RoleId` INT NOT NULL,
  `CreatedDate` TIMESTAMP DEFAULT NOW(),
  PRIMARY KEY (`UserId`),
  FOREIGN KEY (RoleId) REFERENCES RoleMeta(RoleId)
  );
CREATE INDEX IndexUserRoleId ON User (RoleId);

CREATE TABLE `CourseEnrollment`.`Course` (
  `CourseId` INT NOT NULL AUTO_INCREMENT,
  `CourseName` VARCHAR(45) NOT NULL,
  `TeacherId` INT NOT NULL,
  `CreatedDate` TIMESTAMP DEFAULT NOW(),
  PRIMARY KEY (`CourseId`),
  FOREIGN KEY (`TeacherId`) REFERENCES User(UserId)
  );
CREATE INDEX IndexCourseTeacherId ON User (UserId);

CREATE TABLE `CourseEnrollment`.`Section` (
  `SectionId` INT NOT NULL AUTO_INCREMENT,
  `SectionName` VARCHAR(45) NOT NULL,
  `CourseId` INT NOT NULL,
  `CreatedDate` TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (`SectionId`),
    FOREIGN KEY (CourseId) REFERENCES Course(CourseId)
  );
CREATE INDEX IndexSectionCourseId ON Course(CourseId);


CREATE TABLE `CourseEnrollment`.`StudentSection` (
`StudentId` INT NOT NULL,
`SectionId` INT NOT NULL,
`CreatedDate` TIMESTAMP DEFAULT NOW(),
  PRIMARY KEY (`StudentId`, `SectionId`),
  FOREIGN KEY (StudentId) REFERENCES User(UserId),
  FOREIGN KEY (SectionId) REFERENCES Section(SectionId)
);