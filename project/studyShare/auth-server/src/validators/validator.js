const Joi = require('joi');
const errorMessage = require('../errormessage/error.message');

module.exports = {
    register: Joi.object({
        userId: Joi.string()
            .pattern(new RegExp('^[a-z0-9]{4,12}$'))
            .required()
            .messages({
                'string.pattern.base': errorMessage.INVALID_ID_FORMAT
            }),
        email: Joi.string().email().required(),
        password: Joi.string()
            .min(8)
            .pattern(new RegExp('^[A-Za-z0-9!@#$%^&*(),.?":{}|<>]+$'))
            .required()
            .messages({
                'string.pattern.base': errorMessage.INVALID_PASSWORD_FORMAT
            }),
        name: Joi.string().min(2).max(10).required(),
        phone: Joi.string().pattern(new RegExp('^[0-9]{10,11}$')).required().messages({
            'string.pattern.base': errorMessage.INVALID_PHONE_FORMAT
        }),
    }),

    login: Joi.object({
        userId: Joi.string().required(),
        password: Joi.string().required(),
        rememberMe: Joi.boolean().optional().default(false)
    }),

    updatePassword: Joi.object({
        // currentPassword: Joi.string().required(),
        newPassword: Joi.string()
            .min(8)
            .pattern(new RegExp('^[A-Za-z0-9!@#$%^&*(),.?":{}|<>]+$'))
            .required()
            .messages({
                'string.pattern.base': errorMessage.INVALID_PASSWORD_FORMAT
            })
    }),

    updatePhone: Joi.object({
        phone: Joi.string().pattern(new RegExp('^[0-9]{10,11}$')).required().messages({
            'string.pattern.base': errorMessage.INVALID_PHONE_FORMAT
        }),
    }),

    resetPassword: Joi.object({
        userId: Joi.string().required(),
        token: Joi.string().required(),
        newPassword: Joi.string()
            .min(8)
            .pattern(new RegExp('^[A-Za-z0-9!@#$%^&*(),.?":{}|<>]+$'))
            .required()
            .messages({
                'string.pattern.base': errorMessage.INVALID_PASSWORD_FORMAT
            })
    }),
    
}