const router = require('express').Router();
const UserController = require('../controllers/user.controller');
const { verifyToken } = require('../middleware/auth.middleware');
// const validator = require('../validators/validator');
// const userController = new UserController();

// POST /api/user/register - 회원가입
router.post('/register', UserController.register);

// PUT /api/user/update-user - 회원정보 수정 (인증 필요)
router.put('/update-user', verifyToken, UserController.updateUser);

// PUT /api/user/update-phone - 전화번호 변경 (인증 필요)
router.put('/update-phone', verifyToken, UserController.updatePhone);

// PUT /api/user/update-password - 비밀번호 변경 (인증 필요)
router.put('/update-password', verifyToken, UserController.updatePassword);

// POST /api/user/reset-password - 비밀번호 재설정 (인증 불 필요, 비밀번호 재설정 토큰 필요,비밀번호 분실시 사용)
router.post('/reset-password', UserController.resetPassword);

// GET /api/user/verify-email - 이메일 인증
router.get('/verify-email', UserController.verifyEmail);

// POST /api/user/reset-password-email - 비밀번호 재설정 메일 발송
router.post('/reset-password-email', UserController.sendPasswordResetEmail);

// GET /api/user/check-id - 아이디 중복 체크
router.get('/check-id', UserController.checkId);

// GET /api/user/check-email - 이메일 중복 체크
router.get('/check-email', UserController.checkEmail);

//탈퇴는 스프링에서 처리


module.exports = router;