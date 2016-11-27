// 编写秒杀交互逻辑
// 模块话编写
var seckill = {
    // 封装秒杀相关的url
    URL: {
        now: function () {
            return "/seckill/time/now";
        },
        exposer: function (seckillId) {
            return "/seckill/" + seckillId + "/exposer";
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    // 详情页秒杀逻辑
    detail: {
        // 详情页初始化
        init: function (params) {
            // 手机验证和登陆，计时操作
            // 规划我们的交互流程
            // 在cookie查找手机号
            var killPhone = $.cookie('killPhone');
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            // 验证手机号
            if (!seckill.validatePhone(killPhone)) {
                // 绑定phone
                // 控制输出
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true, // 显示弹出层
                    backdrop: 'static', // 禁止位置关闭
                    keyboard: false // 关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        // 写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        // 刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</lable>').show(300);
                    }
                });
            }
            $.get(seckill.URL.now(), function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    // 时间判断
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result:' + result);
                }
            });
        }
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        // 时间判断
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            // 秒杀结束
            seckillBox.html('秒杀结束');
        } else if (nowTime < startTime) {
            // 秒杀未开始
            var killTime = new Date(startTime);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀计时:%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                // 获取秒杀地址,控制显示逻辑，执行秒杀
                seckill.handleSeckillKill(seckillId, seckillBox);
            });
        } else {
            // 秒杀进行中
            seckill.handleSeckillKill(seckillId, seckillBox);
        }
    },

    handleSeckillKill: function (seckillId, node) {
        // 处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>').show();
        $.get(seckill.URL.exposer(seckillId), {}, function (result) {
            // 在回掉函数中执行交互流程
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer && exposer['exposed']) {
                    // 开启秒杀
                    // 获取描述地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log('killUrl:' + killUrl); //todo
                    $('#killBtn').one('click', function () {
                        // 绑定秒杀按钮
                        // 1.先禁用按钮
                        $(this).addClass('disabled');
                        // 2.发送秒杀请求,执行秒杀
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                // 3.显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    })
                } else {
                    // 未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result:' + result);
            }
        });
    },

    validatePhone: function (phone) {
        return !!(phone && phone.length == 11 && !isNaN(phone));
    }
};