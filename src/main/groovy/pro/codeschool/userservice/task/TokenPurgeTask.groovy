package pro.codeschool.userservice.task

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import pro.codeschool.userservice.repository.TokenRepository
import pro.codeschool.userservice.utils.DateUtils

import javax.transaction.Transactional

@Service
@Transactional
class TokenPurgeTask {

    private static final Logger LOG = LoggerFactory.getLogger(getClass())

    @Autowired
    TokenRepository tokenRepository

    @Scheduled(fixedRate = 10000L)
    void purgeTokens() {
        LOG.info("Running task to purge old tokens")
        tokenRepository.purgeExpired(DateUtils.now())
    }
}
