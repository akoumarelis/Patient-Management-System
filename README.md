# Patient Management System
This project was created in the context of the course: « Plan Driven and Agile Programming » at Hellenic Mediterranean University by Zaka Ioannis, Koumarelis Anastasios and Babis Christos .

Description :

      1) The project consists of Patient Management System (PMS) which is a part of Hospital Management System (HMS). This project contains seven modules namely-Admin, Receptionist, Doctor, Payment Counter, Clinic Center, Inpatient and Online Registration.
      2) Hospitals must need to maintain the information of the patients very accurately and up to date. The information helps the doctors to treat the patients very efficiently.
      3) Also the hospital must maintain the information of the patients for n number of years and the episodic information.

System Functions :

      Patient’s Information

      1) The whole information regarding different activities in the hospital is entered in the registers manually for future verification.
      2) The patient details like Personal Information are all entered in registers. Each Patient’s information will be maintained in a separate file.
      3) If the patient gets any new ailments, their details, reports must be added to the previous information.
      4) All the information regarding the patient from entering the hospital to leaving the hospital must be maintained accurately.
     
     Doctor’s Information
     
      1) The information regarding all doctors and their qualifications, their personal details all are maintained in a separate file. Each doctor has a unique id.
      2) The doctors’ availability times all are maintained in a separate file.
  

Software Functions :

Study of the Proposed System 

      Collection of data in a standard format designed in an efficient way.

      Create a Database, which should be:
          1.Integrated
          2.Sharable
          3.Reliable
          4.Secure
          
Any incomplete or invalid data will not be accepted as there are thorough checks at each and every stage of all forms.
The software will handle the following functions:

Authorization:

      1) All the staff in the hospital will be given a user name and password for login in to the system.
      2) Different Menu Accessing Permissions will be given to different cadered people.
      3) Depending on the validity of the username and password entered, the software will become accessible to the authorized users. 
      4) Patient Information
      5) Patient personal information like name, age, sex, occupation, fathers name etc. will be maintained.
      6) Each patient is given a unique PIN (patient index number).
      7) Each patient is assigned to a particular doctor.
  
Doctor’s and other staff Information:

      1) Every doctor and staff member will be given a unique id.
      2) Their personal information like contact numbers are maintained.
      3) Their availability on that day will be maintained.
      4) Patient reports and bills
      5) The reports and bills of a patient are stored in the database for further verification.
      6) The cost of each test is maintained.

Functionalities of the modules:

Login:

      Once User enters the valid userid and password user move to the page that displays corresponding services of that user.
  
Administratior:

      1) Administrator is the one who will be having complete authority over the software.
      2) He is the one who will decide to whom the access over the software is to be granted.
      3) He is responsible for entering new user information, updating their information, and deleting the information when the user no longer exists.

Receptionist:

      1) It works round the clock. Initially the patient is admitted as OP.
      2) Depending upon his condition he is then admitted as the IP.
      3) Emergency cases are accepted near the causality Counter.
      4) This module automates the day-to-day administrative activities and provides instant access to other modules, which leads to a better patient care.

Doctor:

      1) The doctor module is the important module of the Patient management system.
      2) Once the doctor enters the hospital, he will log into the system to see the list of patients allotted to him.
      3) When the patient meets the doctor, the doctor will examine him and prescribes him the necessary medicines to be taken and tests if there are any to be performed.
      4) The patient after undergoing all the tests visits the doctor with the test report.
      5) Depending on the results of the test, the patient may be advised to stay at the hospital.

Payment Counter:

      1) The payment counter module handles all types of billing for long-term care.
      2) This module facilitates cashier and billing operations for different categories of patients like Outpatient, Inpatient.
      3) It provides automatic posting of charges like lab tests conducted in different departments.
      4) The payment counter module is extensively flexible by which each of our billing plans can be configured to automatically accept or deny

Clinic Center:

      1) Clinic Center module starts with receiving tests from doctors and also allows laboratory personnel to generate requests.
      2) The Clinic Center Module supports to perform various tests under following disciplines: Biochemistry, microbiology  etc. Tests are performed only after billing is done.
      3) This rule is exempted when the case is declared as urgent.
  
Inpatient:

      1) Depending upon the test results generated by the Clinic Center, the doctor decides the status of the patient i.e., whether he is an inpatient or an outpatient.
      2) In case, if the patient is declared as in, then the respective patient goes to the receptionist counter and the receptionist there, will allot him a bed in the required ward.
      3) At the time of discharge, this inpatient has to pay the maintenance fee along with the test fee and the registration fee depending on the number of days he stayed in the hospital.

Onilne registration:

      1) In Online Registration, the user can “sign up” into the system, so that he can get username and Password(chosen by him/her).
      2) As soon as the patient get username, he can now login to the system and will be provided with different services.
      3) When he/she fills the form, will be given the appointment date, time and the doctor allotted to him by the Receptionist.
      4) He can edit his profile.
