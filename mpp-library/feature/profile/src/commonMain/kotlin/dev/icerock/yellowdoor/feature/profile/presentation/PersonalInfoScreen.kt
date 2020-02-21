/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.feature.profile.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.ClickableWidget
import dev.icerock.moko.widgets.ImageWidget
import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.ScrollWidget
import dev.icerock.moko.widgets.TextWidget
import dev.icerock.moko.widgets.button
import dev.icerock.moko.widgets.clickable
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.image
import dev.icerock.moko.widgets.input
import dev.icerock.moko.widgets.linear
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.scroll
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text

class PersonalInfoScreen(
    private val theme: Theme,
    private val strings: Strings,
    private val styles: Styles,
    private val images: Images,
    private val createViewModelBlock: (
        EventsDispatcher<PersonalInfoViewModel.EventsListener>
    ) -> PersonalInfoViewModel,
    private val closeRoute: Route<Unit>,
    private val newsRoute: Route<Unit>,
    private val regionRoute: Route<Unit>
) : WidgetScreen<Args.Empty>(), NavigationItem, PersonalInfoViewModel.EventsListener {

    private val viewModel: PersonalInfoViewModel by lazy {
        getViewModel {
            createViewModelBlock(createEventsDispatcher())
        }
    }

    override val navigationBar: NavigationBar = NavigationBar.Normal(
        title = strings.title,
        styles = styles.navigationBar,
        backButton = NavigationBar.Normal.BarButton(
            icon = images.closeImage,
            action = {
                viewModel.didTapCloseButton()
            }
        )
    )

    override val isDismissKeyboardOnTap: Boolean = false

    override fun createContentWidget() = with(theme) {
        viewModel.eventsDispatcher.listen(this@PersonalInfoScreen, this@PersonalInfoScreen)

        constraint(size = WidgetSize.AsParent) {
            val scroll = +scroll(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                id = Id.Scroll,
                child = linear(
                    size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.WrapContent)
                ) {
                    val avatarImage = +image(
                        size = WidgetSize.Const(SizeSpec.Exact(48.0f), SizeSpec.Exact(48.0f)),
                        image = viewModel.avatarImage,
                        id = Id.AvatarImage
                    )

                    val uploadPhotoButton = +button(
                        category = styles.uploadNewPhotoButton,
                        id = Id.UploadNewPhotoButton,
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                        content = ButtonWidget.Content.Text(Value.data(strings.uploadNewPhoto)),
                        onTap = viewModel::didTapUploadNewPhotoButton
                    )

                    val uploadPhotoButtonImage = +image(
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                        id = Id.RightArrowImage,
                        image = const(images.rightButtonArrow)
                    )

                    val birthdayField = +createField(
                        title = strings.birthday,
                        text = viewModel.birthday,
                        didTapBlock = viewModel::didTapBirthday
                    )

                    val regionField = +createField(
                        title = strings.region,
                        text = viewModel.region,
                        didTapBlock = viewModel::didTapRegion
                    )

                    val cityField = +createField(
                        title = strings.city,
                        text = viewModel.city,
                        didTapBlock = viewModel::didTapCity
                    )

                    val educationField = +input(
                        category = styles.textField,
                        size = WidgetSize.AsParent,
                        field = viewModel.education,
                        id = Id.EducationInput,
                        label = const(strings.education),
                        maxLines = MutableLiveData<Int?>(16).readOnly()
                    )

                    val aboutField = +input(
                        category = styles.textField,
                        size = WidgetSize.AsParent,
                        field = viewModel.about,
                        id = Id.EducationInput,
                        label = const(strings.aboutMe),
                        maxLines = MutableLiveData<Int?>(16).readOnly()
                    )

                    val signUpButton = +button(
                        category = styles.yellowButton,
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(46.0f)),
                        id = Id.SaveButton,
                        content = ButtonWidget.Content.Text(Value.data(strings.save)),
                        onTap = viewModel::didTapSaveButton
                    )

                    /*constraints {
                        avatarImage.topToTop(root.safeArea).offset(32)
                        avatarImage.leftToLeft(root.safeArea).offset(16)

                        uploadPhotoButtonImage.rightToRight(root.safeArea).offset(16)
                        uploadPhotoButtonImage.centerYToCenterY(avatarImage)

                        uploadPhotoButton.rightToLeft(uploadPhotoButtonImage).offset(16)
                        uploadPhotoButton.centerYToCenterY(uploadPhotoButtonImage)

                        birthdayField.leftRightToLeftRight(root.safeArea).offset(16)
                        birthdayField.topToBottom(avatarImage).offset(32)

                        regionField.leftRightToLeftRight(root.safeArea).offset(16)
                        regionField.topToBottom(birthdayField).offset(32)

                        cityField.leftRightToLeftRight(root.safeArea).offset(16)
                        cityField.topToBottom(regionField).offset(32)

                        educationField.leftRightToLeftRight(root.safeArea).offset(16)
                        educationField.topToBottom(cityField).offset(32)

                        aboutField.leftRightToLeftRight(root.safeArea).offset(16)
                        aboutField.topToBottom(educationField).offset(32)

                        signUpButton.leftRightToLeftRight(root.safeArea).offset(16)
                        signUpButton.topToBottom(aboutField).offset(32)
                    }*/
                })

            constraints {
                scroll.topToTop(root.safeArea)
                scroll.leftRightToLeftRight(root.safeArea)
                scroll.bottomToBottom(root.safeArea)
            }
        }
    }

    override fun routeToNews() {

    }

    override fun routeToRegionSelection() {
        println("1")
        regionRoute.route(arg = Unit)
    }

    private fun createField(
        title: StringDesc,
        text: LiveData<String>,
        didTapBlock: (() -> Unit)
    ): ClickableWidget<WidgetSize.Const<SizeSpec.AsParent, SizeSpec.AsParent>> = with(theme) {
        val widget = constraint(size = WidgetSize.AsParent) {
            val arrowImage = +image(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                id = Id.RightArrowImage,
                image = const(images.rightButtonArrow)
            )

            val titleText = +text(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                category = styles.selectableFieldTitle,
                text = const(title)
            )

            val contentText = +text(
                size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.MatchConstraint),
                category = styles.selectableFieldContent,
                text = text.map { value: String ->
                    if (value.length == 0)
                        return@map strings.notIndicated

                    return@map value.desc()
                }
            )

            constraints {
                arrowImage.rightToRight(root)
                arrowImage.centerYToCenterY(root)

                titleText.topToTop(root)
                titleText.leftToLeft(root)

                contentText.topToBottom(titleText).offset(4)
                contentText.leftToLeft(root)
                contentText.rightToLeft(arrowImage).offset(8)
                contentText.bottomToBottom(root)
            }
        }

        return clickable(child = widget, onClick = {
            println("test")
            didTapBlock()
        })
    }

    class Styles(
        val navigationBar: NavigationBar.Normal.Styles,
        val uploadNewPhotoButton: ButtonWidget.Category,
        val selectableFieldTitle: TextWidget.Category,
        val selectableFieldContent: TextWidget.Category,
        val textField: InputWidget.Category,
        val yellowButton: ButtonWidget.Category
    )

    interface Strings {
        val title: StringDesc
        val uploadNewPhoto: StringDesc
        val notIndicated: StringDesc
        val birthday: StringDesc
        val region: StringDesc
        val city: StringDesc
        val education: StringDesc
        val aboutMe: StringDesc
        val photo: StringDesc
        val video: StringDesc
        val save: StringDesc
    }

    interface Images {
        val avatarPlaceholderImage: Image
        val closeImage: ImageResource
        val rightButtonArrow: Image
    }

    object Id {
        object AvatarImage : ImageWidget.Id
        object UploadNewPhotoButton : ButtonWidget.Id
        object RightArrowImage : ImageWidget.Id
        object Scroll : ScrollWidget.Id
        object SelectableFieldTitle : TextWidget.Id
        object SelectableFieldContent : TextWidget.Id
        object EducationInput : InputWidget.Id
        object SaveButton : ButtonWidget.Id
    }
}
