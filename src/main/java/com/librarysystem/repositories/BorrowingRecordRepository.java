package com.librarysystem.repositories;

import com.librarysystem.models.BorrowingRecord;
import com.librarysystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {
    List<BorrowingRecord> findByUser(User user);
}
