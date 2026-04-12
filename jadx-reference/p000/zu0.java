package p000;

import android.animation.AnimatorSet;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.search.C0220a2;
import com.google.android.material.search.C0221a3;
import com.google.android.material.search.C0222a4;
import com.google.android.material.search.C0223a5;
import com.google.android.material.search.C0224a6;
import com.google.android.material.search.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class zu0 implements View.OnClickListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f61589a0;

    /* renamed from: a1 */
    public final /* synthetic */ SearchView f61590a1;

    public /* synthetic */ zu0(SearchView searchView, int i) {
        this.f61589a0 = i;
        this.f61590a1 = searchView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.f61589a0) {
            case 0:
                SearchView searchView = this.f61590a1;
                if (!searchView.f49753c4.equals(SearchView.TransitionState.f49760a3)) {
                    SearchView.TransitionState transitionState = searchView.f49753c4;
                    SearchView.TransitionState transitionState2 = SearchView.TransitionState.f49759a2;
                    if (!transitionState.equals(transitionState2)) {
                        final C0224a6 c0224a6 = searchView.f49743b4;
                        SearchView searchView2 = c0224a6.f49766a0;
                        ClippableRoundedCornerLayout clippableRoundedCornerLayout = c0224a6.f49768a2;
                        if (c0224a6.f49778b2 != null) {
                            EditText editText = c0224a6.f49774a8;
                            if (searchView2.m211088a2()) {
                                searchView2.m211089a3();
                            }
                            searchView2.setTransitionState(transitionState2);
                            Toolbar toolbar = c0224a6.f49772a6;
                            Menu menu = toolbar.getMenu();
                            if (menu != null) {
                                menu.clear();
                            }
                            if (c0224a6.f49778b2.getMenuResId() == -1 || !searchView2.f49749c0) {
                                toolbar.setVisibility(8);
                            } else {
                                toolbar.mo209928b2(c0224a6.f49778b2.getMenuResId());
                                ActionMenuView actionMenuViewM213510b5 = kg1.m213510b5(toolbar);
                                if (actionMenuViewM213510b5 != null) {
                                    for (int i = 0; i < actionMenuViewM213510b5.getChildCount(); i++) {
                                        View childAt = actionMenuViewM213510b5.getChildAt(i);
                                        childAt.setClickable(false);
                                        childAt.setFocusable(false);
                                        childAt.setFocusableInTouchMode(false);
                                    }
                                }
                                toolbar.setVisibility(0);
                            }
                            editText.setText(c0224a6.f49778b2.getText());
                            editText.setSelection(editText.getText().length());
                            clippableRoundedCornerLayout.setVisibility(4);
                            final int i2 = 0;
                            clippableRoundedCornerLayout.post(new Runnable() { // from class: kv0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i2) {
                                        case 0:
                                            C0224a6 c0224a62 = c0224a6;
                                            AnimatorSet animatorSetM211094a2 = c0224a62.m211094a2(true);
                                            animatorSetM211094a2.addListener(new C0220a2(c0224a62));
                                            animatorSetM211094a2.start();
                                            break;
                                        default:
                                            C0224a6 c0224a63 = c0224a6;
                                            c0224a63.f49768a2.setTranslationY(r1.getHeight());
                                            AnimatorSet animatorSetM211098a6 = c0224a63.m211098a6(true);
                                            animatorSetM211098a6.addListener(new C0222a4(c0224a63));
                                            animatorSetM211098a6.start();
                                            break;
                                    }
                                }
                            });
                        } else {
                            if (searchView2.m211088a2()) {
                                searchView2.postDelayed(new av0(searchView2, 2), 150L);
                            }
                            clippableRoundedCornerLayout.setVisibility(4);
                            final int i3 = 1;
                            clippableRoundedCornerLayout.post(new Runnable() { // from class: kv0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i3) {
                                        case 0:
                                            C0224a6 c0224a62 = c0224a6;
                                            AnimatorSet animatorSetM211094a2 = c0224a62.m211094a2(true);
                                            animatorSetM211094a2.addListener(new C0220a2(c0224a62));
                                            animatorSetM211094a2.start();
                                            break;
                                        default:
                                            C0224a6 c0224a63 = c0224a6;
                                            c0224a63.f49768a2.setTranslationY(r1.getHeight());
                                            AnimatorSet animatorSetM211098a6 = c0224a63.m211098a6(true);
                                            animatorSetM211098a6.addListener(new C0222a4(c0224a63));
                                            animatorSetM211098a6.start();
                                            break;
                                    }
                                }
                            });
                        }
                        searchView.setModalForAccessibility(true);
                        break;
                    }
                }
                break;
            case 1:
                SearchView searchView3 = this.f61590a1;
                if (!searchView3.f49753c4.equals(SearchView.TransitionState.f49758a1) && !searchView3.f49753c4.equals(SearchView.TransitionState.f49757a0)) {
                    C0224a6 c0224a62 = searchView3.f49743b4;
                    SearchView searchView4 = c0224a62.f49766a0;
                    if (c0224a62.f49778b2 != null) {
                        if (searchView4.m211088a2()) {
                            searchView4.m211087a1();
                        }
                        AnimatorSet animatorSetM211094a2 = c0224a62.m211094a2(false);
                        animatorSetM211094a2.addListener(new C0221a3(c0224a62));
                        animatorSetM211094a2.start();
                    } else {
                        if (searchView4.m211088a2()) {
                            searchView4.m211087a1();
                        }
                        AnimatorSet animatorSetM211098a6 = c0224a62.m211098a6(false);
                        animatorSetM211098a6.addListener(new C0223a5(c0224a62));
                        animatorSetM211098a6.start();
                    }
                    searchView3.setModalForAccessibility(false);
                    break;
                }
                break;
            default:
                SearchView searchView5 = this.f61590a1;
                searchView5.f49738a9.setText("");
                searchView5.m211089a3();
                break;
        }
    }
}
